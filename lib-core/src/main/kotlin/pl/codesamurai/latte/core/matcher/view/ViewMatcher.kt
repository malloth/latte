package pl.codesamurai.latte.core.matcher.view

import android.view.View
import pl.codesamurai.latte.core.ktx.filterIf
import pl.codesamurai.latte.core.ktx.mapIf
import pl.codesamurai.latte.core.matcher.MatchFlag
import pl.codesamurai.latte.core.matcher.MatchFlag.MATCH_ACTIVE_ROOTS
import pl.codesamurai.latte.core.matcher.MatchFlag.MATCH_CONTENT
import pl.codesamurai.latte.core.matcher.MatchPredicate
import pl.codesamurai.latte.core.matcher.view.hierarchy.DepthFirstViewTreeWalk
import pl.codesamurai.latte.core.matcher.view.hierarchy.RootProvider
import pl.codesamurai.latte.core.matcher.view.hierarchy.ViewTreeWalk
import pl.codesamurai.latte.core.matcher.view.hierarchy.WindowRootProvider
import pl.codesamurai.latte.core.matching.thread.checker.IdleChecker
import pl.codesamurai.latte.core.matching.thread.checker.MainThreadIdleChecker

internal typealias ViewMatcher = (View) -> Boolean

@PublishedApi
internal inline fun <reified T : View> matchViews(
    matchFlags: Set<MatchFlag>,
    noinline matchPredicate: MatchPredicate<T>,
    viewMatcherFactory: (MatchPredicate<T>) -> ViewMatcher = ViewMatcherFactory::create,
    viewTreeWalk: ViewTreeWalk = DepthFirstViewTreeWalk,
    idleChecker: IdleChecker = MainThreadIdleChecker,
    rootProvider: RootProvider = WindowRootProvider
): List<T> {
    idleChecker.waitUntilIdle()

    val roots = rootProvider.roots

    if (roots.isEmpty()) {
        return emptyList()
    }

    val matchActiveRoots = matchFlags.contains(MATCH_ACTIVE_ROOTS)
    val matchContent = matchFlags.contains(MATCH_CONTENT)
    val viewMatcher = viewMatcherFactory(matchPredicate)

    return roots.filterIf(matchActiveRoots) { it.isActive }
        .mapIf(matchContent) { it.content ?: it }
        .flatMap { viewTreeWalk.walk(it.view, viewMatcher) }
        .filterIsInstance<T>()
}

@PublishedApi
internal inline fun <reified T : View> matchChildViews(
    parentView: View,
    noinline matchPredicate: MatchPredicate<T>,
    viewMatcherFactory: (MatchPredicate<T>) -> ViewMatcher = ViewMatcherFactory::create,
    viewTreeWalk: ViewTreeWalk = DepthFirstViewTreeWalk
): List<T> {
    val viewMatcher = viewMatcherFactory(matchPredicate)

    return viewTreeWalk.walk(parentView, viewMatcher)
        .filterIsInstance<T>()
}