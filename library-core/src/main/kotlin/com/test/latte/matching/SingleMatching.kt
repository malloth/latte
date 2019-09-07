package com.test.latte.matching

import android.view.View
import com.test.latte.thread.runInUiThread
import com.test.latte.verifier.ResultiveVerifier
import com.test.latte.verifier.VerificationResult

@PublishedApi
internal class SingleMatching<T : View>(
    val view: T,
    private val threadRunner: (() -> Any) -> Any = ::runInUiThread
) : Matching<T> {

    override fun interact(interactor: T.() -> Unit): Matching<T> {
        threadRunner {
            interactor(view)
            true
        }
        return this
    }

    override fun verify(verifier: T.() -> Boolean): Matching<T> {
        val verificationResult = threadRunner {
            verifier(view)
        } as Boolean

        if (!verificationResult) {
            throw AssertionError("View with id '${view.id}' did not pass verification")
        }
        return this
    }

    override fun verifyWithResult(verifier: ResultiveVerifier<T>): Matching<T> {
        val verificationResult = threadRunner {
            verifier(view)
        } as VerificationResult

        if (!verificationResult.isSuccess) {
            throw AssertionError(verificationResult.failureDescription)
        }
        return this
    }
}