package pl.codesamurai.latte.core.thread.idle_checker

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

@PublishedApi
internal object IdleCheckerImpl : IdleChecker {

    private val instrumentation = getInstrumentation()

    override fun waitUntilIdle() = instrumentation.waitForIdleSync()
}