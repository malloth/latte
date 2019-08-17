package com.test.latte.matcher

import android.view.View
import com.test.latte.matcher.MatchType.*
import com.test.latte.matcher.exception.MatchException
import com.test.latte.matching.Matching
import com.test.latte.matching.MultipleMatching
import com.test.latte.matching.SingleMatching

/**
 * Type alias for generic matcher predicate.
 */
typealias MatchPredicate<T> = T.() -> Boolean

/**
 * Matches view of a specified type using given [matchPredicate].
 *
 * By default match lookup is performed only in active window content.
 * To change this behaviour one of [matchFlags] can be passed.
 *
 * Also only a single match is expected. To change this, assign
 * matching strategy through modification of [matchType] argument.
 *
 * @param matchType view matching strategy
 * @param matchFlags flags for matching control
 * @param matchPredicate predicate describing which view should be considered a match
 * @return instance of [Matching] containing matched view(s)
 * @throws MatchException if no matching view was found, or
 * multiple matches were found and [matchType] was set to [MatchType.SINGLE]
 */
inline fun <reified T : View> match(
    matchType: MatchType = SINGLE,
    matchFlags: Int = MATCH_DEFAULT,
    noinline matchPredicate: MatchPredicate<T>
): Matching<T> {
    val matches = matchViews(matchFlags, matchPredicate)

    return when (matches.size) {
        0 -> throw MatchException("No views found matching given criteria")
        1 -> SingleMatching(matches[0])
        else -> when (matchType) {
            SINGLE -> throw MatchException("Found ${matches.size} views matching given criteria")
            FIRST -> SingleMatching(matches.first())
            ALL -> MultipleMatching(matches)
        }
    }
}

/**
 * Ensures that no matching view of a specified type exists in the
 * view hierarchy using given [matchPredicate].
 *
 * By default match lookup is performed only in active window content.
 * To change this behaviour one of [matchFlags] can be passed.
 *
 * @param matchFlags flags for matching control
 * @param matchPredicate predicate describing which view should be considered a match
 * @throws MatchException if any matching view was found
 */
inline fun <reified T : View> noMatch(
    matchFlags: Int = MATCH_DEFAULT,
    noinline matchPredicate: MatchPredicate<T>
) {
    val matches = matchViews(matchFlags, matchPredicate)

    if (matches.isNotEmpty()) {
        throw MatchException("Found ${matches.size} views matching given criteria")
    }
}

/**
 * Flag for matching views only inside active windows (activities, dialogs, popups).
 */
const val MATCH_ACTIVE_ROOTS = 0b00000001
/**
 * Flag for matching views inside content view.
 */
const val MATCH_CONTENT = 0b00000010
/**
 * Flag specifying default matching behaviour.
 */
const val MATCH_DEFAULT = MATCH_CONTENT or MATCH_ACTIVE_ROOTS