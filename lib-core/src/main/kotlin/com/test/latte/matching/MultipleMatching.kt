package com.test.latte.matching

import android.view.View
import com.test.latte.interactor.Interactor
import com.test.latte.thread.runInUiThread
import com.test.latte.util.debugId
import com.test.latte.verifier.VerificationResult
import com.test.latte.verifier.Verifier

@PublishedApi
internal class MultipleMatching<T : View>(
    val views: List<T>,
    private val threadRunner: (() -> Comparable<Boolean>) -> Comparable<Boolean> = ::runInUiThread
) : Matching<T> {

    override fun interact(interactor: Interactor<T>): Matching<T> {
        threadRunner {
            views.forEach(interactor)
            true
        }
        return this
    }

    override fun verify(verifier: Verifier<T>): Matching<T> {
        var id: String? = null
        val result = threadRunner {
            for (view in views) {
                val r = verifier(view)

                if (r.compareTo(false) == 0) {
                    id = view.debugId
                    return@threadRunner r
                }
            }
            true
        }

        if (result.compareTo(false) == 0) {
            val message = when (result) {
                is VerificationResult -> result.failureDescription
                else -> "View with id '${id}' did not pass verification"
            }
            throw AssertionError(message)
        }
        return this
    }
}