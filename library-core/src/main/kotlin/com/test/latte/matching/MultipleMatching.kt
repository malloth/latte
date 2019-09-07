package com.test.latte.matching

import android.view.View
import android.view.View.NO_ID
import com.test.latte.thread.runInUiThread
import com.test.latte.verifier.ResultiveVerifier
import com.test.latte.verifier.VerificationResult

@PublishedApi
internal class MultipleMatching<T : View>(
    val views: List<T>,
    private val threadRunner: (() -> Unit) -> Unit = ::runInUiThread
) : Matching<T> {

    override fun interact(interactor: T.() -> Unit): Matching<T> {
        threadRunner {
            views.forEach(interactor)
        }
        return this
    }

    override fun verify(verifier: T.() -> Boolean): Matching<T> {
        var verificationResult = true
        var id = NO_ID

        threadRunner {
            for (view in views) {
                val result = verifier(view)

                if (!result) {
                    verificationResult = result
                    id = view.id
                    break
                }
            }
        }

        if (!verificationResult) {
            throw AssertionError("View with id '$id' did not pass verification")
        }
        return this
    }

    override fun verifyWithResult(verifier: ResultiveVerifier<T>): Matching<T> {
        var verificationResult: VerificationResult? = null

        threadRunner {
            for (view in views) {
                val result = verifier(view)

                if (!result.isSuccess) {
                    verificationResult = result
                    break
                }
            }
        }

        verificationResult?.let {
            if (!it.isSuccess) {
                throw AssertionError(it.failureDescription)
            }
        }
        return this
    }
}