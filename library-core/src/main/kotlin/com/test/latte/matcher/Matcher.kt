package com.test.latte.matcher

import android.view.View
import com.test.latte.exception.MatchException
import com.test.latte.matcher.MatchType.*
import com.test.latte.matching.Matching
import com.test.latte.matching.MultipleMatching
import com.test.latte.matching.SingleMatching

inline fun <reified T : View> match(
    matchType: MatchType = SINGLE,
    matchFlags: Int = MATCH_DEFAULT,
    crossinline matcher: T.() -> Boolean
): Matching<T> {
    val matches = matchViews(matchFlags, matcher)

    if (matches.isEmpty()) {
        throw MatchException("No views found matching given criteria")
    }

    return when (matchType) {
        SINGLE -> if (matches.size == 1) {
            SingleMatching(matches.first())
        } else {
            throw MatchException("Found ${matches.size} views matching given criteria")
        }
        FIRST -> SingleMatching(matches.first())
        ALL -> MultipleMatching(matches)
    }
}

inline fun <reified T : View> noMatch(
    matchFlags: Int = MATCH_DEFAULT,
    crossinline matcher: T.() -> Boolean
) {
    val matches = matchViews(matchFlags, matcher)

    if (matches.isNotEmpty()) {
        throw MatchException("Found ${matches.size} views matching given criteria")
    }
}

const val MATCH_ACTIVE_ROOTS = 1 shl 0
const val MATCH_CONTENT = 1 shl 1

const val MATCH_DEFAULT = MATCH_CONTENT or MATCH_ACTIVE_ROOTS