package pl.codesamurai.latte.core.verifier

import android.view.View
import pl.codesamurai.latte.core.ktx.debugId
import pl.codesamurai.latte.core.ktx.dropStackTraces
import pl.codesamurai.latte.core.matching.Matching
import pl.codesamurai.latte.core.matching.MatchingDsl

/**
 * Type alias for verification predicate.
 */
public typealias Verification<T> = T.() -> Boolean

/**
 * Method for verifying view's state.
 *
 * @param verification verification block
 */
@MatchingDsl
public fun <T : View> T.verify(verification: Verification<T>) {
    if (!verification(this)) {
        throw AssertionError("View with id '$debugId' did not pass verification")
            .dropStackTraces(1)
    }
}

/**
 * Method for verifying all matching views' state.
 *
 * @param verification verification block
 */
public fun <T : View> Matching<T>.verify(verification: Verification<T>) {
    val view = matches.firstOrNull { !verification(it) }

    if (view != null) {
        throw AssertionError("View with id '${view.debugId}' did not pass verification")
            .dropStackTraces(1)
    }
}