package com.test.latte.thread

import androidx.test.platform.app.InstrumentationRegistry

@PublishedApi
internal object MainThreadObserver : ThreadObserver {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    override fun waitUntilIdle() = instrumentation.waitForIdleSync()
}