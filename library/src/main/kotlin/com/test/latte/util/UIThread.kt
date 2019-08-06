package com.test.latte.util

import android.os.Looper
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

internal fun <R : Any> runInUiThread(block: () -> R): R {
    return if (isInUIThread()) {
        block()
    } else {
        lateinit var result: R

        getInstrumentation().runOnMainSync {
            result = block()
        }
        result
    }
}

private fun isInUIThread(): Boolean =
    Looper.myLooper() == Looper.getMainLooper()