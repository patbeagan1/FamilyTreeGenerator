package com.pbeagan

import kotlin.random.Random

fun main() {
    val personProvider = PersonProvider(
        maleNames,
        femaleNames,
        surnames,
        Random(432)
    )
    val me = IPerson.Male(Person(
        nameFirst = "ME",
        nameMiddle = "ME",
        nameLast = "ME"
    ))

    personProvider.generateAncestors(me, 4)
    personProvider.generateDescendants(me, 4)
    me.generateGraph(3)
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
