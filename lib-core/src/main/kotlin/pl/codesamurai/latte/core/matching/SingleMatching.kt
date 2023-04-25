package pl.codesamurai.latte.core.matching

import android.view.View
import pl.codesamurai.latte.core.interactor.Interactor
import pl.codesamurai.latte.core.thread.runInUiThread
import pl.codesamurai.latte.core.util.debugId
import pl.codesamurai.latte.core.verifier.VerificationResult
import pl.codesamurai.latte.core.verifier.Verifier

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