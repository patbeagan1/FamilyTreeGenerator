package com.pbeagan.entities

import com.pbeagan.providers.DotnameProvider

interface IPerson : DotnameProvider {
    val nameFirst: String
    var nameMiddle: String
    var nameLast: String
    var union: Union?
    var parentUnion: Union?

    fun searchAncestors(
        descendantSearchParams: DescendantSearchParams,
        ancestorSearchParams: AncestorSearchParams,
    ) {
        parentUnion?.let {
            ancestorSearchParams.onUnionFound(it)
            it.parent1.searchAncestors(
                descendantSearchParams,
                ancestorSearchParams.copy(depth = ancestorSearchParams.depth - 1)
            )
            it.parent2.searchAncestors(
                descendantSearchParams,
                ancestorSearchParams.copy(depth = ancestorSearchParams.depth - 1)
            )
            it.children.forEach { child ->
                child.searchDescendants(descendantSearchParams.copy(
                    depth = descendantSearchParams.depth - ancestorSearchParams.depth
                ))
            }
        }
    }

    data class DescendantSearchParams(
        val depth: Int,
        val onUnionFound: (Union) -> Unit,
    )

    data class AncestorSearchParams(
        val depth: Int,
        val onUnionFound: (Union) -> Unit,
    )

    fun searchDescendants(params: DescendantSearchParams) {
        union?.let {
            params.onUnionFound(it)
            it.children.forEach { child ->
                child.searchDescendants(params.copy(depth = params.depth - 1))
            }
        }
    }

    data class Female(val person: IPerson) : IPerson by person {
        fun formUnion(person: Male) = formUnion(person, this)
    }

    data class Male(val person: IPerson) : IPerson by person {
        fun formUnion(person: Female) = formUnion(this, person)
    }
}

fun formUnion(
    parent1: IPerson.Male,
    parent2: IPerson.Female,
): Union {
    val union = Union(parent1, parent2)
    parent1.union = union
    parent2.union = union
    return union
}
