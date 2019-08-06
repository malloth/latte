package com.test.latte.hierarchy

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.test.latte.matcher.ViewMatcher
import com.test.latte.util.debugId

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
        ViewMatcher.TAG, "├─".repeat(currentDepth) +
                "${view::class.java.simpleName} { ${view.debugId} }" +
                if (isMatch) " *** MATCH ***" else ""
    )
}