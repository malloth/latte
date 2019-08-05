package com.test.latte.matcher

import android.view.View
import com.test.latte.matcher.MatchType.*
import com.test.latte.matching.Matching
import com.test.latte.matching.MultipleMatching
import com.test.latte.matching.SingleMatching

inline fun <reified T : View> match(
    matchType: MatchType = SINGLE,
    crossinline matcher: T.() -> Boolean
): Matching<T> {
    val viewMatcher = ViewMatcher()
    val matches = viewMatcher.matchViews(matcher)

    if (matches.isEmpty()) {
        throw AssertionError("No views found matching given criteria.")
    }

    return when (matchType) {
        SINGLE -> if (matches.size == 1) {
            SingleMatching(matches.first())
        } else {
            throw AssertionError("Found ${matches.size} views matching given criteria.")
        }
        FIRST -> SingleMatching(matches.first())
        ALL -> MultipleMatching(matches)
    }
}

inline fun <reified T : View> noMatch(
    crossinline matcher: T.() -> Boolean
) {
    val viewMatcher = ViewMatcher()
    val matches = viewMatcher.matchViews(matcher)

    if (matches.isNotEmpty()) {
        throw AssertionError("Found ${matches.size} views matching given criteria.")
    }
}