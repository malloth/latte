package com.test.latte.matcher

import android.view.View
import androidx.annotation.WorkerThread
import com.test.latte.matcher.MatchType.SINGLE
import com.test.latte.matcher.exception.MatchException
import com.test.latte.matching.Matching
import com.test.latte.matching.matchingFor
import com.test.latte.matching.noMatchingFor

/**
 * Type alias for generic matcher predicate.
 */
public typealias MatchPredicate<T> = T.() -> Boolean

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
@WorkerThread
public inline fun <reified T : View> match(
    matchType: MatchType = SINGLE,
    matchFlags: Int = MATCH_DEFAULT,
    noinline matchPredicate: MatchPredicate<T>
): Matching<T> = matchingFor(matchViews(matchFlags, matchPredicate), matchType)

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
@WorkerThread
public inline fun <reified T : View> noMatch(
    matchFlags: Int = MATCH_DEFAULT,
    noinline matchPredicate: MatchPredicate<T>
): Unit = noMatchingFor(matchViews(matchFlags, matchPredicate))

/**
 * Flag for matching views only inside active windows (activities, dialogs, popups).
 */
public const val MATCH_ACTIVE_ROOTS: Int = 0b00000001

/**
 * Flag for matching views inside content view.
 */
public const val MATCH_CONTENT: Int = 0b00000010

/**
 * Flag specifying default matching behaviour.
 */
public const val MATCH_DEFAULT: Int = MATCH_CONTENT or MATCH_ACTIVE_ROOTS