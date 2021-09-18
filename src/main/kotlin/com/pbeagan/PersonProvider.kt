package com.pbeagan

import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class PersonProvider(
    private val commonNameListMale: List<String>,
    private val commonNameListFemale: List<String>,
    private val surnameList: List<String>,
    private val random: Random,
) {
    private val atomicInteger = AtomicInteger()
    private fun idGen() = atomicInteger.getAndAdd(1).toString()

    fun randomUnisex(params: Params = Default) =
        if (random.nextBoolean()) randomMale(params) else randomFemale(params)

    fun randomMale(params: Params = Default): IPerson.Male {
        val person = Person(
            "${idGen()} ${commonNameListMale.random()}",
            commonNameListMale.random(),
            surnameList.random()
        )
        return IPerson.Male(person.apply {
            when (params) {
                Default -> Unit
                is WithFamily -> generateAncestors(person, params.depth)
            }
        })
    }

    fun randomFemale(params: Params = Default): IPerson.Female {
        val person = Person(
            "${idGen()} ${commonNameListFemale.random()}",
            commonNameListFemale.random(),
            surnameList.random()
        )
        return IPerson.Female(
            person.apply {
                when (params) {
                    Default -> Unit
                    is WithFamily -> generateAncestors(person, params.depth)
                }
            }
        )
    }

    fun generateAncestors(person: IPerson, depth: Int) {
        if (depth <= 0) return
        val parent1 = randomMale().also {
            it.nameLast = person.nameLast
            generateAncestors(it, depth - 1)
        }
        val parent2 = randomFemale().also {
            generateAncestors(it, depth - 1)
        }
        val union = Union(parent1, parent2).also {
            it.children += person
        }
        person.parentUnion = union
        parent1.union = union
        parent2.union = union
    }

    fun generateDescendants(parent1: IPerson.Male, parent2: IPerson.Female, depth: Int) {
        if (depth <= 0) return
        val union = Union(parent1, parent2)
        parent1.union = union
        parent2.union = union

        union.apply {
            repeat(1 + (random.nextInt() % 3) + (random.nextInt() % 3)) {
                children += randomUnisex().apply {
                    nameLast = parent1.nameLast
                    parentUnion = union
                }
            }
        }

        union.children.forEach { this.generateDescendants(it, depth) }
    }

    fun generateDescendants(person: IPerson, depth: Int) {
        when (person) {
            is IPerson.Male -> generateDescendants(
                person,
                randomFemale(WithFamily(depth - 1)),
                depth - 1
            )
            is IPerson.Female -> generateDescendants(
                randomMale(WithFamily(depth - 1)),
                person,
                depth - 1
            )
            else -> throw Exception("Needs to be a gender")
        }
    }

    sealed class Params
    object Default : Params()
    class WithFamily(val depth: Int) : Params()
}