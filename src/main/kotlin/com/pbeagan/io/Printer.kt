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

        findAncestorsAndCousins(person, depth, connections)
        findDescendantsAndInlaws(person, depth, connections)

        connections.forEach { out.append(it) }
        out.append("}")
        return out.toString()
    }

    private fun findDescendantsAndInlaws(
        person: IPerson,
        depth: Int,
        connections: MutableSet<String>,
    ) {
        person.searchDescendants(IPerson.DescendantSearchParams(depth) { union ->
            renderInto(connections)(union)
            findAncestorsAndCousins(union.parent1, depth, connections)
            findAncestorsAndCousins(union.parent2, depth, connections)
        })
    }

    private fun findAncestorsAndCousins(
        person: IPerson,
        depth: Int,
        connections: MutableSet<String>,
    ) {
        person.searchAncestors(
            IPerson.DescendantSearchParams(depth, renderInto(connections)),
            IPerson.AncestorSearchParams(depth, renderInto(connections))
        )
    }

    private fun renderInto(connections: MutableSet<String>): (Union) -> Unit = { union ->
        connections.add("${union.dotName()} [shape=\"rectangle\" color=\"green\"]")
        (union.children + union.parent1 + union.parent2).forEach { person ->
            when (person) {
                is Male -> connections.add("${person.dotName()} [shape=\"ellipse\" color=\"blue\"]")
                is Female -> connections.add("${person.dotName()} [shape=\"ellipse\" color=\"red\"]")
            }
        }
        connections.add("${union.parent1.dotName()} -> ${union.dotName()}")
        connections.add("${union.parent2.dotName()} -> ${union.dotName()}")
        union.children.forEach {
            connections.add("${union.dotName()} -> ${it.dotName()}")
        }
    }
}