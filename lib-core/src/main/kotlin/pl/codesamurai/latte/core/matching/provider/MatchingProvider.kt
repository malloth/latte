package pl.codesamurai.latte.core.matching.provider

import android.view.View
import pl.codesamurai.latte.core.ktx.dropStackTraces
import pl.codesamurai.latte.core.matcher.MatchException
import pl.codesamurai.latte.core.matcher.MatchType
import pl.codesamurai.latte.core.matcher.MatchType.ALL
import pl.codesamurai.latte.core.matcher.MatchType.FIRST
import pl.codesamurai.latte.core.matcher.MatchType.SINGLE
import pl.codesamurai.latte.core.matching.Matching
import pl.codesamurai.latte.core.matching.MultipleMatching
import pl.codesamurai.latte.core.matching.SingleMatching

@PublishedApi
internal fun <T : View> matchingFor(
    matches: List<T>,
    matchType: MatchType = SINGLE
): Matching<T> = when (matches.size) {
    0 -> throw MatchException("No views found matching given criteria").dropStackTraces(1)
    1 -> SingleMatching(matches.first())
    else -> when (matchType) {
        SINGLE -> {
            throw MatchException("Found ${matches.size} views matching given criteria")
                .dropStackTraces(1)
        }
        FIRST -> SingleMatching(matches.first())
        ALL -> MultipleMatching(matches)
    }
}

@PublishedApi
internal fun <T : View> noMatchingFor(matches: List<T>) {
    if (matches.isNotEmpty()) {
        throw MatchException("Found ${matches.size} views matching given criteria")
            .dropStackTraces(1)
    }
}