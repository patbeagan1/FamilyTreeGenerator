package com.pbeagan.io

import com.pbeagan.entities.IPerson
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
        val onUnionFound: (IPerson, Union) -> Unit = { person, union ->
            connections.add("${union.parent1.dotName()} -> ${union.dotName()}")
            connections.add("${union.parent2.dotName()} -> ${union.dotName()}")
            connections.add("${union.dotName()} -> ${person.dotName()}")
        }
        person.searchAncestors(depth, onUnionFound)
        person.searchDescendants(depth) { union ->
            union.parent1.searchAncestors(depth, onUnionFound)
            union.parent2.searchAncestors(depth, onUnionFound)
            connections.add("${union.parent1.dotName()} -> ${union.dotName()}")
            connections.add("${union.parent2.dotName()} -> ${union.dotName()}")
            union.children.forEach {
                connections.add("${union.dotName()} -> ${it.dotName()}")
            }
        }
        connections.forEach { out.append(it) }
        out.append("}")
        return out.toString()
    }
}