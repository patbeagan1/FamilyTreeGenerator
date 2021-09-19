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
        personProvider.generateAncestors(sourcePerson, ancestorDepth)
        personProvider.generateDescendants(sourcePerson, descendantDepth)

        fileManager.writeToFile(
            printer.generateGraph(sourcePerson, graphDepth),
            File("family.dot")
        )

        externalProcessManager.generateAndOpenImage()
    }

    data class Params(
        val sourcePerson: IPerson,
        val ancestorDepth: Int,
        val descendantDepth: Int,
        val graphDepth: Int,
    )
}


