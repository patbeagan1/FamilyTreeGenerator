package com.pbeagan

import com.pbeagan.io.IPrinter
import java.io.File

interface IExternalProcessManager {
    fun generateAndOpenImage()
}

class ExternalProcessManager(
    private val printer: IPrinter,
) : IExternalProcessManager {
    override fun generateAndOpenImage() {
        printer.printProcessError(
            ProcessBuilder("dot", "-Tjpg", "./family.dot")
                .redirectOutput(File("./out.jpg"))
                .start()
        ).waitFor()

        printer.printProcessError(
            ProcessBuilder("open", "out.jpg")
                .start()
        ).waitFor()
    }
}