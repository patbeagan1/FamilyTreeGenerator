import com.pbeagan.IExternalProcessManager
import com.pbeagan.Runtime
import com.pbeagan.entities.IPerson
import com.pbeagan.entities.Person
import com.pbeagan.io.IFileManager
import com.pbeagan.io.Printer
import com.pbeagan.providers.PersonProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import kotlin.random.Random

internal class MainKtTest {
    @Test
    fun `test produces the same result on multiple runs`() {
        val output: String? = generateSimpleGraph(
            PersonProvider(
                listOf("a", "b", "c", "d", "e", "f"),
                listOf("A", "B", "C", "D", "E", "F"),
                listOf("Z", "Y", "X", "W", "V", "U", "T"),
                Random(23223)
            ),
            Runtime.Params(
                IPerson.Male(Person("ME", "ME", "ME")),
                2,
                2,
                1
            ))
        assertEquals(
            """digraph {"0 b c ME" -> "0 b c ME and 3 B B T""3 B B T" -> "0 b c ME and 3 B B T""0 b c ME and 3 B B T" -> "ME ME ME""1 a c ME" -> "1 a c ME and 2 D A Z""2 D A Z" -> "1 a c ME and 2 D A Z""1 a c ME and 2 D A Z" -> "0 b c ME""4 e c T" -> "4 e c T and 5 D A Z""5 D A Z" -> "4 e c T and 5 D A Z""4 e c T and 5 D A Z" -> "3 B B T""7 b c T" -> "7 b c T and 8 B C U""8 B C U" -> "7 b c T and 8 B C U""7 b c T and 8 B C U" -> "6 F B T""ME ME ME" -> "ME ME ME and 6 F B T""6 F B T" -> "ME ME ME and 6 F B T""ME ME ME and 6 F B T" -> "9 C A ME""ME ME ME and 6 F B T" -> "10 b e ME""ME ME ME and 6 F B T" -> "11 a d ME"}""",
            output
        )
    }

    @Test
    fun `test produces the same result on multiple runs, without inlaw family`() {
        val output: String? = generateSimpleGraph(
            PersonProvider(
                listOf("a", "b", "c", "d", "e", "f"),
                listOf("A", "B", "C", "D", "E", "F"),
                listOf("Z", "Y", "X", "W", "V", "U", "T"),
                Random(23223),
                false
            ),
            Runtime.Params(
                IPerson.Male(Person("ME", "ME", "ME")),
                2,
                2,
                1
            ))
        assertEquals(
            """digraph {"0 b c ME" -> "0 b c ME and 3 B B T""3 B B T" -> "0 b c ME and 3 B B T""0 b c ME and 3 B B T" -> "ME ME ME""1 a c ME" -> "1 a c ME and 2 D A Z""2 D A Z" -> "1 a c ME and 2 D A Z""1 a c ME and 2 D A Z" -> "0 b c ME""4 e c T" -> "4 e c T and 5 D A Z""5 D A Z" -> "4 e c T and 5 D A Z""4 e c T and 5 D A Z" -> "3 B B T""ME ME ME" -> "ME ME ME and 6 F B T""6 F B T" -> "ME ME ME and 6 F B T""ME ME ME and 6 F B T" -> "7 c a ME""ME ME ME and 6 F B T" -> "8 a d ME"}""",
            output
        )
    }

    private fun generateSimpleGraph(
        personProvider: PersonProvider,
        params: Runtime.Params,
    ): String? {
        var output: String? = null
        val fileManager = object : IFileManager {
            override fun writeToFile(text: String, file: File) {
                output = text
            }
        }
        val sub = Runtime(
            fileManager,
            Printer(),
            personProvider,
            object : IExternalProcessManager {
                override fun generateAndOpenImage() = Unit
            }
        )
        sub.run(params)
        return output
    }
}