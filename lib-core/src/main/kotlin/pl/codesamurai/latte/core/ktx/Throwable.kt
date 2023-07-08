package pl.codesamurai.latte.core.ktx

internal fun Throwable.dropStackTraces(count: Int): Throwable = also {
    it.stackTrace = it.stackTrace.drop(count).toTypedArray()
}