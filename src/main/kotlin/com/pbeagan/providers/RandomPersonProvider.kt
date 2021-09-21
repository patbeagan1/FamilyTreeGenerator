package com.pbeagan.providers

import com.pbeagan.entities.IPerson
import com.pbeagan.entities.Person
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.abs
import kotlin.random.Random

class RandomPersonProvider(
    private val random: Random,
    private val commonNameListMale: List<String>,
    private val commonNameListFemale: List<String>,
    private val surnameList: List<String>,
) {
    private val atomicInteger = AtomicInteger()
    private fun idGen() = atomicInteger.getAndAdd(1).toString()

    fun randChildCount(): Int {
        // output (1d2-1)+(1d2-1)+(1d3-1)
        // https://anydice.com
        // 2 is most common. Between [0, 4]
        return (abs(random.nextInt()) % 2) +
                (abs(random.nextInt()) % 2) +
                (abs(random.nextInt()) % 3)
    }

    fun randomUnisex() =
        if (random.nextBoolean()) randomMale() else randomFemale()

    fun randomMale(): IPerson.Male = IPerson.Male(
        Person(
            "${idGen()} ${commonNameListMale.random(random)}",
            commonNameListMale.random(random),
            surnameList.random(random)
        )
    )

    fun randomFemale(): IPerson.Female = IPerson.Female(
        Person(
            "${idGen()} ${commonNameListFemale.random(random)}",
            commonNameListFemale.random(random),
            surnameList.random(random)
        )
    )
}