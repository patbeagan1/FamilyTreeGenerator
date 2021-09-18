package com.pbeagan

import java.io.File
import java.io.FileNotFoundException
import kotlin.random.Random

fun main() {
    val personProvider = PersonProvider(
        maleNames,
        femaleNames,
        surnames,
        Random(
            23223
//        23942
        )
    )
    val me = IPerson.Male(Person(
        nameFirst = "ME",
        nameMiddle = "ME",
        nameLast = "ME"
    ))

    personProvider.generateAncestors(me, 4)
    personProvider.generateDescendants(me, 4)
    val familyText = me.generateGraph(3)
    writeToFile(familyText, File("family.dot"))
    val process = ProcessBuilder("./generateImage.sh").start()
    process.errorStream.reader(Charsets.UTF_8).use {
        println(it.readText())
    }
}

private fun writeToFile(familyText: String, file: File) {
    val onFail: (Throwable) -> Unit = {
        println("Sorry, could not write to file!\n" +
                "Failed with: $it")
    }
    try {
        file.writeText(familyText)
    } catch (e: FileNotFoundException) {
        onFail(e)
    } catch (e: SecurityException) {
        onFail(e)
    }
}

private val maleNames = listOf(
    "Tom",
    "Steve",
    "Bob",
    "Jeff",
    "George",
    "Liam",
    "Noah",
    "Oliver",
    "Elijah",
    "William",
    "James",
    "Benjamin",
    "Lucas",
    "Henry",
    "Alexander",
)
private val femaleNames = listOf(
    "Corinne",
    "Sally",
    "Doris",
    "Susan",
    "Alice",
    "Olivia",
    "Emma",
    "Ava",
    "Charlotte",
    "Sophia",
    "Amelia",
    "Isabella",
    "Mia",
    "Evelyn",
)
private val surnames = listOf(
    "Smith",
    "Bellmont",
    "Uchiha",
    "Boudin",
    "Maher",
    "Mullin",
    "Smith",
    "Johnson",
    "Williams",
    "Brown",
    "Jones",
    "Garcia",
    "Miller",
    "Davis",
    "Rodriguez",
    "Martinez",
    "Hernandez",
    "Lopez",
    "Gonzales",
    "Wilson",
    "Anderson",
    "Thomas",
    "Taylor",
    "Moore",
    "Jackson",
    "Martin",
    "Lee",
    "Perez",
    "Thompson",
    "White",
)
