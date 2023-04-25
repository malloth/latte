package pl.codesamurai.latte.core.util

@PublishedApi
internal fun Int.hasFlags(flags: Int): Boolean = this and flags == flags