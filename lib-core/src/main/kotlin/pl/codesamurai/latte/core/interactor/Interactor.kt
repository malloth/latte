package pl.codesamurai.latte.core.interactor

import android.view.View
import pl.codesamurai.latte.core.matching.Matching
import pl.codesamurai.latte.core.matching.thread.runInUiThread

@PublishedApi
internal operator fun <T : View> Matching<T>.invoke(interaction: T.() -> Unit) {
    runInUiThread {
        matches.forEach(interaction)
    }
}