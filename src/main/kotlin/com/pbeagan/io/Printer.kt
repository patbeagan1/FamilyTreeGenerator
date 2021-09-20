package com.pbeagan.io

import com.pbeagan.entities.IPerson
import com.pbeagan.entities.IPerson.Female
import com.pbeagan.entities.IPerson.Male
import com.pbeagan.entities.Union

interface IPrinter {
    fun printProcessError(process: Process): Process
    fun generateGraph(person: IPerson, depth: Int): String
}

class Printer : IPrinter {
    override fun printProcessError(process: Process): Process {
        process.errorStream.reader(Charsets.UTF_8).use { reader ->
            print(reader.readText())
        }
        return process
    }

    override fun generateGraph(person: IPerson, depth: Int): String {
        val out = StringBuilder()
        out.append("digraph {")
        val connections = mutableSetOf<String>()
        val onUnionFound: (IPerson, Union) -> Unit = { localPerson, union ->
            connections.registerStyles(union)
            connections.addParents(union)
            connections.addChild(union, localPerson)
        }
        person.searchAncestors(depth, onUnionFound)
        person.searchDescendants(depth) { union ->
            union.parent1.searchAncestors(depth, onUnionFound)
            union.parent2.searchAncestors(depth, onUnionFound)
            connections.registerStyles(union)
            connections.addParents(union)
            union.children.forEach {
                connections.addChild(union, it)
            }
        }
        connections.forEach { out.append(it) }
        out.append("}")
        return out.toString()
    }

    private fun MutableSet<String>.registerStyles(union: Union) {
        add("${union.dotName()} [shape=\"rectangle\" color=\"green\"]")
        (union.children + union.parent1 + union.parent2).forEach { person ->
            when (person) {
                is Male -> add("${person.dotName()} [shape=\"ellipse\" color=\"blue\"]")
                is Female -> add("${person.dotName()} [shape=\"ellipse\" color=\"red\"]")
            }
        }
    }

    private fun MutableSet<String>.addChild(
        union: Union,
        person: IPerson,
    ) {
        add("${union.dotName()} -> ${person.dotName()}")
    }

    private fun MutableSet<String>.addParents(union: Union) {
        add("${union.parent1.dotName()} -> ${union.dotName()}")
        add("${union.parent2.dotName()} -> ${union.dotName()}")
    }
}