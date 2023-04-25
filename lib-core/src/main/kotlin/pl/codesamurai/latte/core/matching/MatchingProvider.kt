package pl.codesamurai.latte.core.matching

import android.view.View
import pl.codesamurai.latte.core.matcher.MatchType
import pl.codesamurai.latte.core.matcher.MatchType.ALL
import pl.codesamurai.latte.core.matcher.MatchType.FIRST
import pl.codesamurai.latte.core.matcher.MatchType.SINGLE
import pl.codesamurai.latte.core.matcher.exception.MatchException

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