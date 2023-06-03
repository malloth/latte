package pl.codesamurai.latte.core.matching.thread.checker

@PublishedApi
internal interface IdleChecker {

    fun waitUntilIdle()
}