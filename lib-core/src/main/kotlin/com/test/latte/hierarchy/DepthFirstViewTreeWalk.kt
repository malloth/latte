package com.test.latte.hierarchy

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.test.latte.core.BuildConfig.DEBUG
import com.test.latte.hierarchy.ViewTreeWalk.Companion.TAG
import com.test.latte.util.debugId

@PublishedApi
internal object DepthFirstViewTreeWalk : ViewTreeWalk {

    override fun walk(view: View, matcher: (View) -> Boolean): List<View> =
        performViewTreeWalk(view, matcher)

    private fun performViewTreeWalk(
        view: View,
        matcher: (View) -> Boolean,
        matches: MutableList<View> = mutableListOf(),
        currentDepth: Int = 0
    ): List<View> {
        val isMatch = matcher(view)

        if (isMatch) {
            matches += view
        }

        if (DEBUG) {
            printViewHierarchy(view, currentDepth, isMatch)
        }

        if (view is ViewGroup) {
            view.children.forEach {
                performViewTreeWalk(it, matcher, matches, currentDepth + 1)
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

    private val ViewGroup.children: Array<View>
        get() = Array(childCount, ::getChildAt)
}