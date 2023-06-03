package pl.codesamurai.latte.core.matcher.view

import android.view.View
import pl.codesamurai.latte.core.ktx.filterIf
import pl.codesamurai.latte.core.ktx.hasFlags
import pl.codesamurai.latte.core.ktx.mapIf
import pl.codesamurai.latte.core.matcher.MATCH_ACTIVE_ROOTS
import pl.codesamurai.latte.core.matcher.MATCH_CONTENT
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
    matchFlags: Int,
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

    val matchActiveRoots = matchFlags.hasFlags(MATCH_ACTIVE_ROOTS)
    val matchContent = matchFlags.hasFlags(MATCH_CONTENT)
    val viewMatcher = viewMatcherFactory(matchPredicate)

    return roots.filterIf(matchActiveRoots) { it.isActive }
        .mapIf(matchContent) { it.content ?: it }
        .flatMap { viewTreeWalk.walk(it.view, viewMatcher) }
        .filterIsInstance<T>()
}