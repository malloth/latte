package pl.codesamurai.latte.core.matcher

import android.view.View

@PublishedApi
internal object ViewMatcherFactory {

    inline fun <reified T : View> create(noinline matchPredicate: MatchPredicate<T>): ViewMatcher =
        { it is T && matchPredicate(it) }
}