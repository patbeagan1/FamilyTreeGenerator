package com.pbeagan.providers

interface DotnameProvider {
    fun dotName(): String

    fun String.wrapQuote() = "\"" + this + "\""
}