package pl.codesamurai.latte.core.matcher

import android.view.View
import pl.codesamurai.latte.core.hierarchy.DepthFirstViewTreeWalk
import pl.codesamurai.latte.core.hierarchy.RootProvider
import pl.codesamurai.latte.core.hierarchy.ViewTreeWalk
import pl.codesamurai.latte.core.hierarchy.WindowRootProvider
import pl.codesamurai.latte.core.thread.IdleChecker
import pl.codesamurai.latte.core.thread.MainThreadIdleChecker
import pl.codesamurai.latte.core.util.filterIf
import pl.codesamurai.latte.core.util.hasFlags
import pl.codesamurai.latte.core.util.mapIf

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