package pl.codesamurai.latte.core.thread.idle_checker

@PublishedApi
internal interface IdleChecker {

    fun waitUntilIdle()
}