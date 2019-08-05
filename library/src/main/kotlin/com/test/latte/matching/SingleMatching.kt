package com.test.latte.matching

import android.view.View
import com.test.latte.util.runInUiThread

@PublishedApi
internal class SingleMatching<T : View>(private val view: T) : Matching<T> {

    override fun interact(interactor: T.() -> Unit): Matching<T> {
        runInUiThread {
            interactor(view)
        }
        return this
    }

    override fun verify(message: String?, verifier: T.() -> Boolean): Matching<T> {
        val result = runInUiThread {
            verifier(view)
        }

        if (!result) {
            throw AssertionError(message ?: "View with id '${view.id}' did not pass verification")
        }
        return this
    }
}