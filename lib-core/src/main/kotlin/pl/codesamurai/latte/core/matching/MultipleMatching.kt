package pl.codesamurai.latte.core.matching

import android.view.View
import pl.codesamurai.latte.core.interactor.Interactor
import pl.codesamurai.latte.core.ktx.debugId
import pl.codesamurai.latte.core.ktx.dropStackTraces
import pl.codesamurai.latte.core.matching.thread.runInUiThread
import pl.codesamurai.latte.core.verifier.Verifier

@PublishedApi
internal class MultipleMatching<T : View>(
    val views: List<T>,
    private val threadRunner: (() -> Boolean) -> Boolean = ::runInUiThread
) : Matching<T> {

    override fun interact(interactor: Interactor<T>) {
        threadRunner {
            views.forEach(interactor)
            true
        }
    }

    override fun verify(verifier: Verifier<T>) {
        var id: String? = null
        val result = threadRunner {
            views.all {
                verifier(it).also { result ->
                    if (!result) {
                        id = it.debugId
                    }
                }
            }
        }

        if (!result) {
            throw AssertionError("View with id '${id}' did not pass verification")
                .dropStackTraces(1)
        }
    }
}