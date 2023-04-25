package com.test.latte.matcher

import android.view.View
import com.test.latte.hierarchy.DepthFirstViewTreeWalk
import com.test.latte.hierarchy.RootProvider
import com.test.latte.hierarchy.ViewTreeWalk
import com.test.latte.hierarchy.WindowRootProvider
import com.test.latte.thread.IdleChecker
import com.test.latte.thread.MainThreadIdleChecker
import com.test.latte.util.filterIf
import com.test.latte.util.hasFlags
import com.test.latte.util.mapIf

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