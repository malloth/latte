package com.test.latte.matching

import android.view.View
import com.test.latte.thread.runInUiThread

@PublishedApi
internal class MultipleMatching<T : View>(private val views: List<T>) : Matching<T> {

    override fun interact(interactor: T.() -> Unit): Matching<T> {
        runInUiThread {
            views.forEach(interactor)
        }
        return this
    }

    override fun verify(message: String?, verifier: T.() -> Boolean): Matching<T> {
        var result = true
        var id = 0

        runInUiThread {
            for (view in views) {
                result = verifier(view)

                if (!result) {
                    id = view.id
                    break
                }
            }
        }

        if (!result) {
            throw AssertionError(message ?: "View with id '$id' did not pass verification")
        }
        return this
    }
}