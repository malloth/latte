package pl.codesamurai.latte.core.matcher.view

import android.view.View
import pl.codesamurai.latte.core.matcher.MatchPredicate

@PublishedApi
internal object ViewMatcherFactory {

    inline fun <reified T : View> create(noinline matchPredicate: MatchPredicate<T>): ViewMatcher =
        { it is T && matchPredicate(it) }
}