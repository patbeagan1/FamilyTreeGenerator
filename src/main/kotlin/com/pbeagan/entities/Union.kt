package com.pbeagan.entities

import com.pbeagan.providers.DotnameProvider
import com.pbeagan.providers.PersonProvider

class Union(
    val parent1: IPerson.Male,
    val parent2: IPerson.Female,
    var children: List<IPerson> = listOf(),
) : DotnameProvider {
    override fun toString() =
        ((parent1.nameFirst + parent1.nameLast to parent2.nameFirst + parent2.nameLast) to children.map {
            it.union ?: it.nameFirst +
            it.nameLast
        }).toString()

    override fun dotName() = ("" +
            "${parent1.nameFirst} ${parent1.nameMiddle} ${parent1.nameLast} and " +
            "${parent2.nameFirst} ${parent2.nameMiddle} ${parent2.nameLast}").wrapQuote()
}