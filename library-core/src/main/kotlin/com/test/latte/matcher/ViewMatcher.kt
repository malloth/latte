package com.test.latte.matcher

import android.util.Log
import android.view.View
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.test.latte.hierarchy.RootProvider
import com.test.latte.hierarchy.walk
import com.test.latte.util.filterIf
import com.test.latte.util.hasFlags
import com.test.latte.util.mapIf

@PublishedApi
internal inline fun <reified T : View> matchViews(
    matchFlags: Int,
    crossinline matcher: T.() -> Boolean
): List<T> {
    Log.d(TAG, "Matching started")

    getInstrumentation().waitForIdleSync()

    val roots = RootProvider.roots

    if (roots.isEmpty()) {
        Log.w(TAG, "No roots were found")
        return emptyList()
    }

    roots.forEach {
        Log.d(TAG, "Found root: root = ${it::class.java.simpleName}, isFocused = ${it.windowId.isFocused}")
    }

    val viewMatcher: (View) -> Boolean = {
        it is T && matcher(it)
    }
    val matchActiveRoots = matchFlags.hasFlags(MATCH_ACTIVE_ROOTS)
    val matchContent = matchFlags.hasFlags(MATCH_CONTENT)

    return roots.filterIf(matchActiveRoots) { it.windowId.isFocused }
        .mapIf(matchContent) { it.findViewById(android.R.id.content) ?: it }
        .flatMap {
            Log.d(TAG, "Matching: parent = ${it::class.java.simpleName}")
            it.walk(viewMatcher)
        }
        .filterIsInstance<T>()
        .also {
            Log.d(TAG, "Found ${it.size} matching view(s)")
        }
}

@PublishedApi
internal const val TAG = "ViewMatcher"