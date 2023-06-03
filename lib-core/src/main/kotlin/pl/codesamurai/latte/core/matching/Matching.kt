package pl.codesamurai.latte.core.matching

import android.view.View
import androidx.annotation.UiThread
import pl.codesamurai.latte.core.interactor.Interactor
import pl.codesamurai.latte.core.verifier.Verifier

/**
 * Interface for performing interactions and verifications over matched view(s).
 */
public interface Matching<T : View> {

    /**
     * Performs given actions on matched view(s).
     *
     * @param interactor set of interactions
     */
    @UiThread
    @MatchingDsl
    public fun interact(interactor: Interactor<T>)

    /**
     * Performs given assertions on matched view(s).
     *
     * @param verifier set of verifications
     */
    @UiThread
    @MatchingDsl
    public fun verify(verifier: Verifier<T>)
}