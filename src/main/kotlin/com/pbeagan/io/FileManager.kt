package com.pbeagan.io

import java.io.File
import java.io.FileNotFoundException
import java.nio.charset.Charset
import kotlin.system.exitProcess

interface INameFileReader {
    fun processNameFile(fileName: String): List<String>
}

interface IFileManager {
    fun writeToFile(text: String, file: File)
}

class FileManager : IFileManager, INameFileReader {
    override fun processNameFile(fileName: String): List<String> {
        try {
            return File(fileName)
                .readLines(Charset.defaultCharset())
                .mapNotNull { s -> s.trim().takeUnless { it.startsWith("#") } }
        } catch (e: FileNotFoundException) {
            println("Sorry, could not find file!\n" +
                    "Failed with: $e")
        } catch (e: SecurityException) {
            println("Sorry, could not access file!\n" +
                    "Failed with: $e")
        }
        exitProcess(1)
    }

    override fun writeToFile(text: String, file: File) {
        try {
            file.writeText(text)
        } catch (e: FileNotFoundException) {
            println("Sorry, could not find file!\n" +
                    "Failed with: $e")
            println("Output would have been:\n" +
                    text)
        } catch (e: SecurityException) {
            println("Sorry, could not access file!\n" +
                    "Failed with: $e")
            println("Output would have been:\n" +
                    text)
        }
    }
}