package com.test.latte.matcher

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.test.latte.util.RootProvider
import com.test.latte.util.debugId

@PublishedApi
internal class ViewMatcher {

    inline fun <reified T : View> matchViews(
        crossinline matcher: T.() -> Boolean
    ): List<T> {
        Log.d(TAG, "Matching started")

        getInstrumentation().waitForIdleSync()

        val roots = RootProvider.roots

        if (roots.isEmpty()) {
            Log.w(TAG, "No roots were found")
            return emptyList()
        } else {
            roots.forEach {
                Log.d(
                    TAG,
                    "Found root: root = ${it.view::class.java.simpleName}, isFocused = ${it.view.windowId.isFocused}"
                )
            }
        }

        val viewMatcher: (View) -> Boolean = {
            it is T && matcher(it)
        }
        val views = mutableListOf<View>()

        roots.filter { it.view.windowId.isFocused }
            .forEach {
                Log.d(TAG, "Matching: root = ${it.view::class.java.simpleName}")

                val base = it.view.findViewById<View>(android.R.id.content) ?: it.view
                base.walk(views, viewMatcher)
            }

        return views.filterIsInstance(T::class.java).also {
            Log.d(TAG, "Found ${it.size} matching view(s)")
        }
    }

    @PublishedApi
    internal fun View.walk(
        matches: MutableList<View>,
        matcher: (View) -> Boolean,
        currentDepth: Int = 0
    ): List<View> {
        val isMatch = matcher(this)

        if (isMatch) {
            matches += this
        }
        printViewHierarchy(this, currentDepth, isMatch)

        if (this is ViewGroup) {
            val children = Array(childCount, ::getChildAt)

            children.forEach {
                it.walk(matches, matcher, currentDepth + 1)
            }
        }
        return matches
    }

    private fun printViewHierarchy(view: View, currentDepth: Int, isMatch: Boolean) {
        Log.d(
            TAG, "├─".repeat(currentDepth) +
                    "${view::class.java.simpleName} { ${view.debugId} }" +
                    if (isMatch) " *** MATCH ***" else ""
        )
    }

    companion object {

        const val TAG = "ViewMatcher"
    }
}