package pl.codesamurai.latte.core.ktx

@PublishedApi
internal inline fun <T> List<T>.filterIf(condition: Boolean, predicate: (T) -> Boolean): List<T> =
    if (condition) filter(predicate) else this

@PublishedApi
internal inline fun <T> List<T>.mapIf(condition: Boolean, transform: (T) -> T): List<T> =
    if (condition) map(transform) else this