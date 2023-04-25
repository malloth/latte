package com.test.latte.thread

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

@PublishedApi
internal object MainThreadIdleChecker : IdleChecker {

    private val instrumentation = getInstrumentation()

    override fun waitUntilIdle() = instrumentation.waitForIdleSync()
}