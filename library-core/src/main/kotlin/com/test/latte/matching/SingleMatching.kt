package com.test.latte.matching

import android.view.View
import com.test.latte.thread.runInUiThread

@PublishedApi
internal class SingleMatching<T : View>(
    private val view: T,
    private val threadRunner: (() -> Boolean) -> Boolean = ::runInUiThread
) : Matching<T> {

    override fun interact(interactor: T.() -> Unit): Matching<T> {
        threadRunner {
            interactor(view)
            true
        }
        return this
    }

    override fun verify(message: String?, verifier: T.() -> Boolean): Matching<T> {
        val result = threadRunner {
            verifier(view)
        }

        if (!result) {
            throw AssertionError(message ?: "View with id '${view.id}' did not pass verification")
        }
        return this
    }
}