package com.test.latte.matching

import android.view.View
import androidx.annotation.UiThread
import com.test.latte.interactor.Interactor
import com.test.latte.verifier.Verifier

/**
 * Interface for performing interactions and verifications over matched view(s).
 */
public interface Matching<T : View> {

    /**
     * Performs given actions on matched view(s).
     *
     * @param interactor set of interactions
     * @return this instance of [Matching]
     */
    @UiThread
    public fun interact(interactor: Interactor<T>): Matching<T>

    /**
     * Performs given assertions on matched view(s).
     *
     * @param verifier set of verifications
     * @return this instance of [Matching]
     */
    @UiThread
    public fun verify(verifier: Verifier<T>): Matching<T>
}