package com.pbeagan

fun String.wrapQuote() = "\"" + this + "\""

fun IPerson.generateGraph(depth: Int) {
    println("digraph {")
    val connections = mutableSetOf<String>()
    val onUnionFound: (IPerson, Union) -> Unit = { person, union ->
        connections.add("${union.parent1.dotName()} -> ${union.dotName()}")
        connections.add("${union.parent2.dotName()} -> ${union.dotName()}")
        connections.add("${union.dotName()} -> ${person.dotName()}")
    }
    this.searchAncestors(depth, onUnionFound)
    this.searchDescendants(depth) { union ->
        union.parent1.searchAncestors(depth, onUnionFound)
        union.parent2.searchAncestors(depth, onUnionFound)
        connections.add("${union.parent1.dotName()} -> ${union.dotName()}")
        connections.add("${union.parent2.dotName()} -> ${union.dotName()}")
        union.children.forEach {
            connections.add("${union.dotName()} -> ${it.dotName()}")
        }
    }
    connections.forEach { println(it) }
    println("}")
}