import org.junit.Test
import org.junit.Assert.assertEquals
import ru.hse.cli.CliRunner
import ru.hse.cli.commands.factory.CliCommandFactory
import java.io.ByteArrayInputStream

class CliRunnerTest {
    private val pipeline1 = listOf(
        CliCommandFactory.createCliCommand("echo", listOf("abc"))
    )

    @Test
    fun testEcho() {
        val input = "echo \$a | cat | cat"
        val istream = ByteArrayInputStream(input.toByteArray(Charsets.UTF_8))
        CliRunner(istream, System.out).run()
    }


}