import org.junit.Test
import org.junit.Assert.assertEquals
import ru.hse.cli.commands.EchoCommand

class CliCommandTest {
    @Test
    fun testEcho() {
        assertEquals("a b c", EchoCommand().execute(listOf("a", "b", "c")))
    }
}