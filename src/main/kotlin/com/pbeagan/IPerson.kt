package com.pbeagan

interface IPerson : DotnameProvider {
    val nameFirst: String
    var nameMiddle: String
    var nameLast: String
    var union: Union?
    var parentUnion: Union?

    data class Female(val person: IPerson) : IPerson by person
    data class Male(val person: IPerson) : IPerson by person

    fun searchAncestors(depth: Int, onUnionFound: (IPerson, Union) -> Unit) {
        parentUnion?.let {
            onUnionFound(this, it)
            it.parent1.searchAncestors(depth - 1, onUnionFound)
            it.parent2.searchAncestors(depth - 1, onUnionFound)
        }
    }

    fun searchDescendants(depth: Int, onUnionFound: (Union) -> Unit) {
        union?.let {
            onUnionFound(it)
            it.children.forEach { child ->
                child.searchDescendants(depth - 1, onUnionFound)
            }
        }
    }
}
