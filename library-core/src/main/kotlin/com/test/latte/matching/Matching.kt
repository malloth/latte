package com.test.latte.matching

import android.view.View
import androidx.annotation.UiThread
import com.test.latte.verifier.ResultiveVerifier
import com.test.latte.verifier.SimpleVerifier
import com.test.latte.verifier.VerificationResult

/**
 * Interface for performing interactions and verifications over matched view(s).
 */
interface Matching<T : View> {

    /**
     * Performs given actions on matched view(s).
     *
     * @param interactor set of interactions
     * @return this instance of [Matching]
     */
    @UiThread
    fun interact(interactor: T.() -> Unit): Matching<T>

    /**
     * Performs given assertions on matched view(s).
     *
     * @param verifier set of verifications
     * @return this instance of [Matching]
     */
    @UiThread
    fun verify(verifier: SimpleVerifier<T>): Matching<T>

    /**
     * Performs given assertions on matched view(s).
     *
     * If assertions fail, it throws returned result's
     * [failureDescription][VerificationResult.failureDescription].
     *
     * @param verifier set of verifications returning a result
     * @return this instance of [Matching]
     */
    @UiThread
    fun verifyWithResult(verifier: ResultiveVerifier<T>): Matching<T>
}