import org.junit.Test
import org.junit.Assert.assertEquals
import ru.hse.cli.CliRunner
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class CliRunnerTest {

    private fun executeCommand(command: String): String {
        val istream = ByteArrayInputStream(command.toByteArray(Charsets.UTF_8))
        val buffer = ByteArrayOutputStream()
        val ostream = PrintStream(buffer)
        CliRunner(istream, ostream).run()
        return buffer.toString()
    }

    @Test
    fun testEcho() {
        assertEquals("a\n", executeCommand("echo a"))
    }

    @Test
    fun testVariable() {
        assertEquals("b\n", executeCommand("a=b\necho \$a"))
    }

    @Test
    fun testCatInput() {
        assertEquals("a\n", executeCommand("echo a | cat"))
    }

    @Test
    fun testCatFile() {
        val tmpFile = createTempFile()
        tmpFile.writeText("a\nbbbb\n")
        tmpFile.deleteOnExit()
        assertEquals("a\nbbbb\n", executeCommand("cat ${tmpFile.absoluteFile}"))
    }

    @Test
    fun testExit() {
        assertEquals("a\n", executeCommand("echo a\nexit\necho b"))
    }

    @Test
    fun testWc() {
        val tmpFile = createTempFile()
        tmpFile.writeText("a\nbbbb\n")
        tmpFile.deleteOnExit()
        assertEquals("       2       2       6\n", executeCommand("cat ${tmpFile.absoluteFile} | wc"))
    }

    @Test
    fun testDoubleQuotes01() {
        assertEquals("b\n", executeCommand("a=\"b\"\necho \"\$a\""))
    }

    @Test
    fun testDoubleQuotes02() {
        assertEquals("'b'\n", executeCommand("a=\"'b\"\necho \"\$a'\""))
    }

    @Test
    fun testSingleQuotes01() {
        assertEquals("\$a\n", executeCommand("echo '\$a'"))
    }

    @Test
    fun testSingleQuotes02() {
        assertEquals("\"\$a\"\n", executeCommand("b='\"\$a\"'\necho \$b"))
    }

    @Test
    fun presentationTest01() {
        assertEquals("Hello, world!\n", executeCommand("echo \"Hello, world!\""))
    }

    @Test
    fun presentationTest02() {
        assertEquals("", executeCommand("x=exit\n\$x"))
    }

    @Test
    fun presentationTest03() {
        assertEquals("       1       1       3\n", executeCommand("echo 123 | wc"))
    }

    @Test
    fun grepTest01() {
        assertEquals("a\n", executeCommand("echo abc | grep a"))
    }

    @Test
    fun grepTest02() {
        val tmpFile = createTempFile()
        tmpFile.writeText("abc\n" +
                "d\n" +
                "ba\n" +
                "c\n")
        tmpFile.deleteOnExit()
        assertEquals("abc\n" +
                "d\n" +
                "a\n" +
                "c\n", executeCommand("cat ${tmpFile.absoluteFile}  | grep a -A 1"))
    }

    @Test
    fun grepTest03() {
        val tmpFile = createTempFile()
        tmpFile.writeText("bab a b")
        tmpFile.deleteOnExit()
        assertEquals("a\n", executeCommand("cat ${tmpFile.absoluteFile}  | grep a -w"))
    }

    @Test
    fun grepTest04() {
        val tmpFile = createTempFile()
        tmpFile.writeText("caseInsensitive")
        tmpFile.deleteOnExit()
        assertEquals("caseInsensitive\n",
            executeCommand("cat ${tmpFile.absoluteFile}  | grep CASEINSENSITIVE -i"))
    }
}