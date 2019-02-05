import org.junit.Test
import org.junit.Assert.assertEquals
import ru.hse.cli.commands.factory.CliCommandFactory

class CliCommandTest {
    @Test
    fun testEcho() {
        val echo = CliCommandFactory.createCliCommand("echo", listOf("a", "b", "c"))
        assertEquals("a b c", echo.execute())
    }

    @Test
    fun testCat() {
    }
}