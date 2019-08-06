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
internal class ViewMatcher {

    inline fun <reified T : View> matchViews(
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
        val views = mutableListOf<View>()

        roots.filterIf(matchFlags.hasFlags(MATCH_FOCUSED)) { it.windowId.isFocused }
            .mapIf(matchFlags.hasFlags(MATCH_CONTENT)) { it.findViewById(android.R.id.content) ?: it }
            .forEach {
                Log.d(TAG, "Matching: root = ${it::class.java.simpleName}")
                it.walk(views, viewMatcher)
            }

        return views.filterIsInstance<T>().also {
            Log.d(TAG, "Found ${it.size} matching view(s)")
        }
    }

    companion object {

        const val TAG = "ViewMatcher"
    }
}