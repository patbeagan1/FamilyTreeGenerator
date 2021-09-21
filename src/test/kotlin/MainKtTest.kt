import com.pbeagan.IExternalProcessManager
import com.pbeagan.Runtime
import com.pbeagan.entities.IPerson
import com.pbeagan.entities.Person
import com.pbeagan.io.IFileManager
import com.pbeagan.io.Printer
import com.pbeagan.providers.PersonProvider
import com.pbeagan.providers.RandomPersonProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import kotlin.random.Random

internal class MainKtTest {
    @Test
    fun `test produces the same result on multiple runs`() {
        val output: String? = generateSimpleGraph(
            PersonProvider(RandomPersonProvider(
                Random(23223),
                listOf("a", "b", "c", "d", "e", "f"),
                listOf("A", "B", "C", "D", "E", "F"),
                listOf("Z", "Y", "X", "W", "V", "U", "T")
            )),
            Runtime.Params(
                IPerson.Male(Person("ME", "ME", "ME")),
                2,
                2,
                1, false, false
            ))
        assertEquals(
            """digraph {"0 b c ME and 3 B B T" [shape="rectangle" color="green"]"ME ME ME" [shape="ellipse" color="blue"]"0 b c ME" [shape="ellipse" color="blue"]"3 B B T" [shape="ellipse" color="red"]"0 b c ME" -> "0 b c ME and 3 B B T""3 B B T" -> "0 b c ME and 3 B B T""0 b c ME and 3 B B T" -> "ME ME ME""1 a c ME and 2 D A Z" [shape="rectangle" color="green"]"1 a c ME" [shape="ellipse" color="blue"]"2 D A Z" [shape="ellipse" color="red"]"1 a c ME" -> "1 a c ME and 2 D A Z""2 D A Z" -> "1 a c ME and 2 D A Z""1 a c ME and 2 D A Z" -> "0 b c ME""ME ME ME and 6 F B T" [shape="rectangle" color="green"]"7 c a ME" [shape="ellipse" color="blue"]"8 a d ME" [shape="ellipse" color="blue"]"6 F B T" [shape="ellipse" color="red"]"ME ME ME" -> "ME ME ME and 6 F B T""6 F B T" -> "ME ME ME and 6 F B T""ME ME ME and 6 F B T" -> "7 c a ME""ME ME ME and 6 F B T" -> "8 a d ME""7 c a ME and 9 A D V" [shape="rectangle" color="green"]"10 a d ME" [shape="ellipse" color="blue"]"11 a a ME" [shape="ellipse" color="blue"]"9 A D V" [shape="ellipse" color="red"]"7 c a ME" -> "7 c a ME and 9 A D V""9 A D V" -> "7 c a ME and 9 A D V""7 c a ME and 9 A D V" -> "10 a d ME""7 c a ME and 9 A D V" -> "11 a a ME""8 a d ME and 12 D B Y" [shape="rectangle" color="green"]"13 E B ME" [shape="ellipse" color="red"]"12 D B Y" [shape="ellipse" color="red"]"8 a d ME" -> "8 a d ME and 12 D B Y""12 D B Y" -> "8 a d ME and 12 D B Y""8 a d ME and 12 D B Y" -> "13 E B ME""4 e c T and 5 D A Z" [shape="rectangle" color="green"]"4 e c T" [shape="ellipse" color="blue"]"5 D A Z" [shape="ellipse" color="red"]"4 e c T" -> "4 e c T and 5 D A Z""5 D A Z" -> "4 e c T and 5 D A Z""4 e c T and 5 D A Z" -> "3 B B T"}""",
            output
        )
    }

    @Test
    fun `test produces the same result on multiple runs, without inlaw family`() {
        val output: String? = generateSimpleGraph(
            PersonProvider(RandomPersonProvider(
                Random(23223),
                listOf("a", "b", "c", "d", "e", "f"),
                listOf("A", "B", "C", "D", "E", "F"),
                listOf("Z", "Y", "X", "W", "V", "U", "T")
            )),
            Runtime.Params(
                IPerson.Male(Person("ME", "ME", "ME")),
                2,
                2,
                1,false, false
            ))
        assertEquals(
            """digraph {"0 b c ME and 3 B B T" [shape="rectangle" color="green"]"ME ME ME" [shape="ellipse" color="blue"]"0 b c ME" [shape="ellipse" color="blue"]"3 B B T" [shape="ellipse" color="red"]"0 b c ME" -> "0 b c ME and 3 B B T""3 B B T" -> "0 b c ME and 3 B B T""0 b c ME and 3 B B T" -> "ME ME ME""1 a c ME and 2 D A Z" [shape="rectangle" color="green"]"1 a c ME" [shape="ellipse" color="blue"]"2 D A Z" [shape="ellipse" color="red"]"1 a c ME" -> "1 a c ME and 2 D A Z""2 D A Z" -> "1 a c ME and 2 D A Z""1 a c ME and 2 D A Z" -> "0 b c ME""ME ME ME and 6 F B T" [shape="rectangle" color="green"]"7 c a ME" [shape="ellipse" color="blue"]"8 a d ME" [shape="ellipse" color="blue"]"6 F B T" [shape="ellipse" color="red"]"ME ME ME" -> "ME ME ME and 6 F B T""6 F B T" -> "ME ME ME and 6 F B T""ME ME ME and 6 F B T" -> "7 c a ME""ME ME ME and 6 F B T" -> "8 a d ME""7 c a ME and 9 A D V" [shape="rectangle" color="green"]"10 a d ME" [shape="ellipse" color="blue"]"11 a a ME" [shape="ellipse" color="blue"]"9 A D V" [shape="ellipse" color="red"]"7 c a ME" -> "7 c a ME and 9 A D V""9 A D V" -> "7 c a ME and 9 A D V""7 c a ME and 9 A D V" -> "10 a d ME""7 c a ME and 9 A D V" -> "11 a a ME""8 a d ME and 12 D B Y" [shape="rectangle" color="green"]"13 E B ME" [shape="ellipse" color="red"]"12 D B Y" [shape="ellipse" color="red"]"8 a d ME" -> "8 a d ME and 12 D B Y""12 D B Y" -> "8 a d ME and 12 D B Y""8 a d ME and 12 D B Y" -> "13 E B ME""4 e c T and 5 D A Z" [shape="rectangle" color="green"]"4 e c T" [shape="ellipse" color="blue"]"5 D A Z" [shape="ellipse" color="red"]"4 e c T" -> "4 e c T and 5 D A Z""5 D A Z" -> "4 e c T and 5 D A Z""4 e c T and 5 D A Z" -> "3 B B T"}""",
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