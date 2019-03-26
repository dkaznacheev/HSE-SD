import org.junit.Test
import org.junit.Assert.assertEquals
import ru.hse.cli.CliRunner
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

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
        assertEquals("a${sep}bbbb$sep", executeCommand("cat ${tmpFile.absoluteFile}"))
    }

    @Test
    fun testExit() {
        assertEquals("a" + sep, executeCommand(
            "echo a${sep}exit${sep}echo b"))
    }

    @Test
    fun testWc() {
        val tmpFile = createTempFile()
        tmpFile.writeText("a${sep}bbbb$sep")
        tmpFile.deleteOnExit()
        assertEquals("       2       2       6$sep", executeCommand(
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
    fun presentationTest03() {
        assertEquals("       1       1       3$sep", executeCommand("echo 123 | wc"))
    }
}