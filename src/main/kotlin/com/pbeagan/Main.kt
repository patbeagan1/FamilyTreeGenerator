package com.pbeagan

import com.pbeagan.Runtime.Params
import com.pbeagan.entities.IPerson
import com.pbeagan.entities.Person
import com.pbeagan.io.FileManager
import com.pbeagan.io.Printer
import com.pbeagan.providers.PersonProvider
import com.pbeagan.providers.RandomPersonProvider
import kotlin.random.Random

fun main() {
    val fileManager = FileManager()
    val printer = Printer()
    val runtime = Runtime(
        fileManager,
        printer,
        PersonProvider(RandomPersonProvider(
            Random,
            fileManager.processNameFile("./namesMale.txt"),
            fileManager.processNameFile("./namesFemale.txt"),
            fileManager.processNameFile("./namesLast.txt"),
        )),
        ExternalProcessManager(printer)
    )

    runtime.run(Params(
        IPerson.Male(
            Person(
                nameFirst = "ME",
                nameMiddle = "ME",
                nameLast = "ME"
            )
        ),
        ancestorDepth = 3,
        descendantDepth = 3,
        shouldGenCousins = true,
        shouldGenInlaws = true
    ))
}
