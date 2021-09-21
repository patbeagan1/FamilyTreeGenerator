package com.pbeagan

import com.pbeagan.entities.IPerson
import com.pbeagan.io.IFileManager
import com.pbeagan.io.IPrinter
import com.pbeagan.providers.PersonProvider
import java.io.File

class Runtime(
    private val fileManager: IFileManager,
    private val printer: IPrinter,
    private val personProvider: PersonProvider,
    private val externalProcessManager: IExternalProcessManager,
) {
    fun run(params: Params) = params.run {
        personProvider.apply {
            generateAncestors(sourcePerson, params.shouldGenCousins, ancestorDepth)
            generateDescendants(sourcePerson, params.shouldGenInlaws, descendantDepth)
        }

        fileManager.writeToFile(
            printer.generateGraph(sourcePerson, maxOf(ancestorDepth, descendantDepth)),
            File("family.dot")
        )

        externalProcessManager.generateAndOpenImage()
    }

    data class Params(
        val sourcePerson: IPerson,
        val ancestorDepth: Int,
        val descendantDepth: Int,
        val shouldGenCousins: Boolean,
        val shouldGenInlaws: Boolean,
    )
}


