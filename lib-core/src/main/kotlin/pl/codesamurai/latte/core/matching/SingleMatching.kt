package pl.codesamurai.latte.core.matching

import android.view.View
import pl.codesamurai.latte.core.interactor.Interactor
import pl.codesamurai.latte.core.ktx.debugId
import pl.codesamurai.latte.core.ktx.dropStackTraces
import pl.codesamurai.latte.core.matching.thread.runInUiThread
import pl.codesamurai.latte.core.verifier.Verifier

@PublishedApi
internal class SingleMatching<T : View>(
    val view: T,
    private val threadRunner: (() -> Boolean) -> Boolean = ::runInUiThread
) : Matching<T> {

    override fun interact(interactor: Interactor<T>) {
        threadRunner {
            interactor(view)
            true
        }
    }

    override fun verify(verifier: Verifier<T>) {
        val result = threadRunner {
            verifier(view)
        }

        if (!result) {
            throw AssertionError("View with id '${view.debugId}' did not pass verification")
                .dropStackTraces(1)
        }
    }
}