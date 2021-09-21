import com.pbeagan.ExternalProcessManager
import com.pbeagan.Runtime
import com.pbeagan.entities.IPerson
import com.pbeagan.entities.Person
import com.pbeagan.io.FileManager
import com.pbeagan.io.Printer
import com.pbeagan.providers.PersonProvider
import com.pbeagan.providers.RandomPersonProvider
import org.junit.Test
import kotlin.random.Random

internal class MainKtVisualizationTest {
    private val person = Person(
        "0000",
        "0000",
        "0000"
    )

    @Test
    fun `test simple graph, with inlaws`() {
        generateSimpleGraph(
            PersonProvider(RandomPersonProvider(
                Random(23223),
                listOf("MA", "MB", "MC", "MD", "ME", "MF"),
                listOf("Fa", "Fb", "Fc", "Fd", "Fe", "Ff"),
                listOf("Z", "Y", "X", "W", "V", "U", "T")
            )),
            Runtime.Params(
                IPerson.Male(person),
                ancestorDepth = 1,
                descendantDepth = 2,
                graphDepth = 2,
                shouldGenCousins = false,
                shouldGenInlaws = true
            ))
    }

    @Test
    fun `test simple graph, with cousins`() {
        generateSimpleGraph(
            PersonProvider(RandomPersonProvider(
                Random(2224),
                listOf("MA", "MB", "MC", "MD", "ME", "MF"),
                listOf("Fa", "Fb", "Fc", "Fd", "Fe", "Ff"),
                listOf("Z", "Y", "X", "W", "V", "U", "T")
            )),
            Runtime.Params(
                IPerson.Male(person),
                ancestorDepth = 2,
                descendantDepth = 2,
                graphDepth = 5,
                shouldGenCousins = true,
                shouldGenInlaws = false
            ))
    }

    @Test
    fun `test simple graph, no inlaws or cousins`() {
        generateSimpleGraph(
            PersonProvider(RandomPersonProvider(
                Random(23223),
                listOf("MA", "MB", "MC", "MD", "ME", "MF"),
                listOf("Fa", "Fb", "Fc", "Fd", "Fe", "Ff"),
                listOf("Z", "Y", "X", "W", "V", "U", "T"),
            )),
            Runtime.Params(
                IPerson.Male(person),
                ancestorDepth = 2,
                descendantDepth = 2,
                graphDepth = 2,
                shouldGenCousins = false,
                shouldGenInlaws = false
            ))
    }

    @Test
    fun `test simple graph, with inlaws and cousins`() {
        generateSimpleGraph(
            PersonProvider(RandomPersonProvider(
                Random(23223),
                listOf("MA", "MB", "MC", "MD", "ME", "MF"),
                listOf("Fa", "Fb", "Fc", "Fd", "Fe", "Ff"),
                listOf("Z", "Y", "X", "W", "V", "U", "T"),
            )),
            Runtime.Params(
                IPerson.Male(person),
                ancestorDepth = 2,
                descendantDepth = 2,
                graphDepth = 10,
                shouldGenCousins = true,
                shouldGenInlaws = true
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