package pl.codesamurai.latte.core.thread

@PublishedApi
internal interface IdleChecker {

    fun waitUntilIdle()
}