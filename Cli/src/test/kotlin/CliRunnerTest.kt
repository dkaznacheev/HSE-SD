import org.junit.Test
import org.junit.Assert.assertEquals
import ru.hse.cli.CliRunner
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.*

class CliRunnerTest {
    private val sep = System.lineSeparator()
    
    private fun executeCommand(command: String): String {
        val istream = ByteArrayInputStream(command.toByteArray(Charsets.UTF_8))
        val buffer = ByteArrayOutputStream()
        val ostream = PrintStream(buffer)
        CliRunner(istream, ostream).run()
        return buffer.toString()
    }

    @Test
    fun testEcho() {
        assertEquals("a$sep", executeCommand("echo a"))
    }

    @Test
    fun testVariable() {
        assertEquals("b$sep", executeCommand("a=b${sep}echo \$a"))
    }

    @Test
    fun testCatInput() {
        assertEquals("a$sep", executeCommand("echo a | cat"))
    }

    @Test
    fun testCatFile() {
        val tmpFile = createTempFile()
        tmpFile.writeText("a${sep}bbbb$sep")
        tmpFile.deleteOnExit()
        assertEquals("a${sep}bbbb$sep$sep", executeCommand("cat ${tmpFile.absoluteFile}"))
    }

    @Test
    fun testExit() {
        assertEquals("a" + sep, executeCommand(
            "echo a${sep}exit${sep}echo b"))
    }

    @Test
    fun testWc() {
        val tmpFile = createTempFile()
        val text = "a${sep}bbbb$sep"
        tmpFile.writeText(text)
        tmpFile.deleteOnExit()
        val newlines = text.split(Regex("(\r\n)|\n|\r")).size
        val words = StringTokenizer(text).countTokens()
        val bytes = text.toByteArray().size
        assertEquals("       $newlines       $words       $bytes$sep", executeCommand(
            "cat ${tmpFile.absoluteFile} | wc"))
    }

    @Test
    fun testDoubleQuotes01() {
        assertEquals("b$sep", executeCommand("a=\"b\"${sep}echo \"\$a\""))
    }

    @Test
    fun testDoubleQuotes02() {
        assertEquals("'b'$sep", executeCommand("a=\"'b\"${sep}echo \"\$a'\""))
    }

    @Test
    fun testSingleQuotes01() {
        assertEquals("\$a$sep", executeCommand("echo '\$a'"))
    }

    @Test
    fun testSingleQuotes02() {
        assertEquals("\"\$a\"$sep", executeCommand("b='\"\$a\"'${sep}echo \$b"))
    }

    @Test
    fun presentationTest01() {
        assertEquals("Hello, world!$sep", executeCommand("echo \"Hello, world!\""))
    }

    @Test
    fun presentationTest02() {
        assertEquals("", executeCommand("x=exit$sep\$x"))
    }

    @Test
    fun pwdTest() {
        assertEquals(System.getProperty("user.dir") + System.lineSeparator(), executeCommand("pwd"))
    }
}