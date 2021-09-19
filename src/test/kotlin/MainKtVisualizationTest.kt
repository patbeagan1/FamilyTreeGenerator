import com.pbeagan.ExternalProcessManager
import com.pbeagan.Runtime
import com.pbeagan.entities.IPerson
import com.pbeagan.entities.Person
import com.pbeagan.io.FileManager
import com.pbeagan.io.Printer
import com.pbeagan.providers.PersonProvider
import org.junit.Test
import kotlin.random.Random

internal class MainKtVisualizationTest {
    /**
     * Output should be [/art/simpleSampleWithInlaw.jpg]
     */
    @Test
    fun `test simple graph, with inlaws`() {
        generateSimpleGraph(
            PersonProvider(
                listOf("MA", "MB", "MC", "MD", "ME", "MF"),
                listOf("Fa", "Fb", "Fc", "Fd", "Fe", "Ff"),
                listOf("Z", "Y", "X", "W", "V", "U", "T"),
                Random(23223),
                true
            ),
            Runtime.Params(
                IPerson.Male(Person("ME", "ME", "ME")),
                2,
                3,
                2
            ))
    }

    /**
     * Output should be [/art/simpleSampleWithoutInlaw.jpg]
     */
    @Test
    fun `test simple graph, without inlaws`() {
        generateSimpleGraph(
            PersonProvider(
                listOf("MA", "MB", "MC", "MD", "ME", "MF"),
                listOf("Fa", "Fb", "Fc", "Fd", "Fe", "Ff"),
                listOf("Z", "Y", "X", "W", "V", "U", "T"),
                Random(23223),
                false
            ),
            Runtime.Params(
                IPerson.Male(Person("ME", "ME", "ME")),
                2,
                3,
                2
            ))
    }

    private fun generateSimpleGraph(
        personProvider: PersonProvider,
        params: Runtime.Params,
    ) {
        val printer = Printer()
        val sub = Runtime(
            FileManager(),
            printer,
            personProvider,
            ExternalProcessManager(printer)
        )
        sub.run(params)
    }
}