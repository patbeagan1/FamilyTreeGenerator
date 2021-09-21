package com.pbeagan.providers

import com.pbeagan.entities.IPerson
import com.pbeagan.entities.formUnion

class PersonProvider(
    private val randomPersonProvider: RandomPersonProvider,
) {
    fun generateAncestors(person: IPerson, shouldGenCousins: Boolean, depth: Int) {
        if (depth <= 0) return
        val union = formUnion(
            randomPersonProvider.randomMale().also {
                it.nameLast = person.nameLast
                generateAncestors(it, shouldGenCousins, depth - 1)
            },
            randomPersonProvider.randomFemale().also {
                generateAncestors(it, shouldGenCousins, depth - 1)
            }
        )

        union.children += person

        if (shouldGenCousins) {
            repeat(randomPersonProvider.randChildCount() - 1) {
                union.children += randomPersonProvider.randomUnisex().also {
                    it.nameLast = person.nameLast
                    it.parentUnion = union
                    generateDescendants(it, false, depth)
                }
            }
        }

        union.children.forEach { it.parentUnion = union }
    }

    fun generateDescendants(
        person: IPerson,
        shouldGenInlaws: Boolean,
        depth: Int,
    ) {
        if (depth <= 0) return
        val union = when (person) {
            is IPerson.Male -> person.formUnion(randomPersonProvider.randomFemale().apply {
                if (shouldGenInlaws) {
                    generateAncestors(this, false, depth)
                }
            })
            is IPerson.Female -> person.formUnion(randomPersonProvider.randomMale().apply {
                if (shouldGenInlaws) {
                    generateAncestors(this, false, depth)
                }
            })
            else -> throw Exception("Needs to be a gender.")
        }

        repeat(randomPersonProvider.randChildCount()) {
            union.children += randomPersonProvider.randomUnisex().also {
                it.nameLast = union.parent1.nameLast
                it.parentUnion = union
            }
        }

        union.children.forEach {
            generateDescendants(it, shouldGenInlaws, depth - 1)
        }
    }
}