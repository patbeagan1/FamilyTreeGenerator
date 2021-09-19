package com.pbeagan.providers

import com.pbeagan.providers.PersonProvider.RandPersonParams.Default
import com.pbeagan.providers.PersonProvider.RandPersonParams.WithFamily
import com.pbeagan.entities.IPerson
import com.pbeagan.entities.Person
import com.pbeagan.entities.Union
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.abs
import kotlin.random.Random

class PersonProvider(
    private val commonNameListMale: List<String>,
    private val commonNameListFemale: List<String>,
    private val surnameList: List<String>,
    private val random: Random,
    private val shouldGenInLawFamily: Boolean = true,
) {
    private val atomicInteger = AtomicInteger()
    private fun idGen() = atomicInteger.getAndAdd(1).toString()

    private fun randomUnisex(randPersonParams: RandPersonParams = Default) =
        if (random.nextBoolean()) randomMale(randPersonParams) else randomFemale(randPersonParams)

    private fun randomMale(randPersonParams: RandPersonParams = Default): IPerson.Male {
        val person = Person(
            "${idGen()} ${commonNameListMale.random(random)}",
            commonNameListMale.random(random),
            surnameList.random(random)
        )
        return IPerson.Male(person.apply {
            when (randPersonParams) {
                Default -> Unit
                is WithFamily -> generateAncestors(person, randPersonParams.depth)
            }
        })
    }

    private fun randomFemale(randPersonParams: RandPersonParams = Default): IPerson.Female {
        val person = Person(
            "${idGen()} ${commonNameListFemale.random(random)}",
            commonNameListFemale.random(random),
            surnameList.random(random)
        )
        return IPerson.Female(
            person.apply {
                when (randPersonParams) {
                    Default -> Unit
                    is WithFamily -> generateAncestors(person, randPersonParams.depth)
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
            repeat(randChildCount()) {
                children += randomUnisex().apply {
                    nameLast = parent1.nameLast
                    parentUnion = union
                }
            }
        }

        union.children.forEach { this.generateDescendants(it, depth) }
    }

    private fun randChildCount(): Int {
        // output (1d2-1)+(1d2-1)+(1d3-1)
        // https://anydice.com
        // 2 is most common. Between [0, 4]
        return (abs(random.nextInt()) % 2) +
                (abs(random.nextInt()) % 2) +
                (abs(random.nextInt()) % 3)
    }

    fun generateDescendants(person: IPerson, depth: Int) {
        val params = if (shouldGenInLawFamily) {
            WithFamily(depth - 1)
        } else {
            Default
        }
        when (person) {
            is IPerson.Male -> generateDescendants(
                person,
                randomFemale(params),
                depth - 1
            )
            is IPerson.Female -> generateDescendants(
                randomMale(params),
                person,
                depth - 1
            )
            else -> throw Exception("Needs to be a gender")
        }
    }

    sealed class RandPersonParams {
        object Default : RandPersonParams()
        class WithFamily(val depth: Int) : RandPersonParams()
    }
}