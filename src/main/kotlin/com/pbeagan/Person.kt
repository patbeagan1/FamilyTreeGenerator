package com.pbeagan

data class Person(
    override val nameFirst: String,
    override var nameMiddle: String,
    override var nameLast: String,
    override var union: Union? = null,
    override var parentUnion: Union? = null,
) : IPerson {
    override fun dotName(): String = "$nameFirst $nameMiddle $nameLast".wrapQuote()
}
