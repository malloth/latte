package com.test.latte.matching

import android.view.View
import com.test.latte.matcher.MatchType
import com.test.latte.matcher.MatchType.ALL
import com.test.latte.matcher.MatchType.FIRST
import com.test.latte.matcher.MatchType.SINGLE
import com.test.latte.matcher.exception.MatchException

@PublishedApi
internal fun <T : View> matchingFor(
    matches: List<T>,
    matchType: MatchType = SINGLE
): Matching<T> = when (matches.size) {
    0 -> throw MatchException("No views found matching given criteria")
    1 -> SingleMatching(matches[0])
    else -> when (matchType) {
        SINGLE -> throw MatchException("Found ${matches.size} views matching given criteria")
        FIRST -> SingleMatching(matches.first())
        ALL -> MultipleMatching(matches)
    }
}

@PublishedApi
internal fun <T : View> noMatchingFor(matches: List<T>) {
    if (matches.isNotEmpty()) {
        throw MatchException("Found ${matches.size} views matching given criteria")
    }
}