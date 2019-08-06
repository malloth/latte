package com.test.latte.util

@PublishedApi
internal fun Int.hasFlags(flags: Int): Boolean = this and flags == flags