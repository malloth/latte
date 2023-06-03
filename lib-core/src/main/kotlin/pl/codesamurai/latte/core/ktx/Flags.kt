package pl.codesamurai.latte.core.ktx

@PublishedApi
internal fun Int.hasFlags(flags: Int): Boolean = this and flags == flags