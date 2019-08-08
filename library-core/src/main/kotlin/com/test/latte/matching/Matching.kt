package com.test.latte.matching

import android.view.View

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
    fun interact(interactor: T.() -> Unit): Matching<T>

    /**
     * Performs given assertions on matched view(s).
     *
     * @param message message which will be printed when assertion fails
     * @param verifier set of verifications
     * @return this instance of [Matching]
     */
    fun verify(message: String? = null, verifier: T.() -> Boolean): Matching<T>
}