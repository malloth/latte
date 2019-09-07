package com.test.latte.matching

import android.view.View
import com.test.latte.interactor.Interactor
import com.test.latte.thread.runInUiThread
import com.test.latte.util.debugId
import com.test.latte.verifier.VerificationResult
import com.test.latte.verifier.Verifier

@PublishedApi
internal class SingleMatching<T : View>(
    val view: T,
    private val threadRunner: (() -> Comparable<Boolean>) -> Comparable<Boolean> = ::runInUiThread
) : Matching<T> {

    override fun interact(interactor: Interactor<T>): Matching<T> {
        threadRunner {
            interactor(view)
            true
        }
        return this
    }

    override fun verify(verifier: Verifier<T>): Matching<T> {
        val result = threadRunner {
            verifier(view)
        }

        if (result.compareTo(false) == 0) {
            val message = when (result) {
                is VerificationResult -> result.failureDescription
                else -> "View with id '${view.debugId}' did not pass verification"
            }
            throw AssertionError(message)
        }
        return this
    }
}