package com.test.latte.matcher

import android.view.View
import com.test.latte.hierarchy.DepthFirstViewTreeWalk
import com.test.latte.hierarchy.RootProvider
import com.test.latte.hierarchy.ViewTreeWalk
import com.test.latte.hierarchy.WindowRootProvider
import com.test.latte.thread.MainThreadObserver
import com.test.latte.thread.ThreadObserver
import com.test.latte.util.filterIf
import com.test.latte.util.hasFlags
import com.test.latte.util.mapIf

@PublishedApi
internal inline fun <reified T : View> matchViews(
    matchFlags: Int,
    noinline matcher: T.() -> Boolean,
    viewMatcherFactory: (T.() -> Boolean) -> ((View) -> Boolean) = ViewMatcherFactory::create,
    viewTreeWalk: ViewTreeWalk = DepthFirstViewTreeWalk,
    threadObserver: ThreadObserver = MainThreadObserver,
    rootProvider: RootProvider = WindowRootProvider
): List<T> {
    threadObserver.waitUntilIdle()

    val roots = rootProvider.roots

    if (roots.isEmpty()) {
        return emptyList()
    }

    val matchActiveRoots = matchFlags.hasFlags(MATCH_ACTIVE_ROOTS)
    val matchContent = matchFlags.hasFlags(MATCH_CONTENT)
    val viewMatcher = viewMatcherFactory(matcher)

    return roots.filterIf(matchActiveRoots) { it.isActive }
        .mapIf(matchContent) { it.content ?: it }
        .flatMap { viewTreeWalk.walk(it.view, viewMatcher) }
        .filterIsInstance<T>()
}