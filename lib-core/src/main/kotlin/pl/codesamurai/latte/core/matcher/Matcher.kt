package pl.codesamurai.latte.core.matcher

import android.view.View
import androidx.annotation.WorkerThread
import pl.codesamurai.latte.core.interactor.invoke
import pl.codesamurai.latte.core.matcher.MatchException.MatchFoundException
import pl.codesamurai.latte.core.matcher.MatchException.MultipleMatchesFoundException
import pl.codesamurai.latte.core.matcher.MatchException.NoMatchFoundException
import pl.codesamurai.latte.core.matcher.MatchFlag.Companion.MATCH_DEFAULT
import pl.codesamurai.latte.core.matcher.MatchType.ALL
import pl.codesamurai.latte.core.matcher.MatchType.FIRST
import pl.codesamurai.latte.core.matcher.MatchType.SINGLE
import pl.codesamurai.latte.core.matcher.view.matchChildViews
import pl.codesamurai.latte.core.matcher.view.matchViews
import pl.codesamurai.latte.core.matching.Matching
import pl.codesamurai.latte.core.matching.MatchingDsl

/**
 * Matches view of a specified type using given [matchPredicate].
 *
 * By default match lookup is performed only in active window content.
 * To change this behaviour one of [matchFlags] can be passed.
 *
 * Also only a single match is expected. To change this, assign
 * matching strategy through modification of [matchType] argument.
 *
 * @param matchPredicate predicate describing which view should be considered a match
 * @param matchType view matching strategy
 * @param matchFlags flags for matching control
 * @param block action to apply on matched view(s)
 * @return matching views
 * @throws MatchException if no matching view was found, or
 * multiple matches were found and [matchType] was set to [MatchType.SINGLE]
 */
@WorkerThread
@MatchingDsl
public inline fun <reified T : View> match(
    noinline matchPredicate: MatchPredicate<T>,
    matchType: MatchType = SINGLE,
    matchFlags: Set<MatchFlag> = MATCH_DEFAULT,
    noinline block: T.() -> Unit = {}
): Matching<T> {
    val matches = matchViews(matchFlags, matchPredicate)
    val matching = when (matches.size) {
        0 -> throw NoMatchFoundException("No views found matching given criteria")
        1 -> matches
        else -> when (matchType) {
            SINGLE -> throw MultipleMatchesFoundException("Found ${matches.size} views matching given criteria")
            FIRST -> listOf(matches.first())
            ALL -> matches
        }
    }.let(::Matching)

    matching(block)

    return matching
}

/**
 * Matches child view of a specified type using given [matchPredicate].
 *
 * Only a single match is expected. To change this, assign
 * matching strategy through modification of [matchType] argument.
 *
 * @param matchPredicate predicate describing which view should be considered a match
 * @param matchType view matching strategy
 * @param block action to apply on matched view(s)
 * @return matching views
 * @throws MatchException if no matching view was found, or
 * multiple matches were found and [matchType] was set to [MatchType.SINGLE]
 */
@WorkerThread
@MatchingDsl
public inline fun <reified T : View> View.matchChild(
    noinline matchPredicate: MatchPredicate<T>,
    matchType: MatchType = SINGLE,
    noinline block: T.() -> Unit = {}
): Matching<T> {
    val matches = matchChildViews(this, matchPredicate)
    val matching = when (matches.size) {
        0 -> throw NoMatchFoundException("No child views found matching given criteria")
        1 -> matches
        else -> when (matchType) {
            SINGLE -> throw MultipleMatchesFoundException("Found ${matches.size} child views matching given criteria")
            FIRST -> listOf(matches.first())
            ALL -> matches
        }
    }.let(::Matching)

    matching(block)

    return matching
}

/**
 * Ensures that no matching view of a specified type exists in the
 * view hierarchy using given [matchPredicate].
 *
 * By default match lookup is performed only in active window content.
 * To change this behaviour one of [matchFlags] can be passed.
 *
 * @param matchPredicate predicate describing which view should be considered a match
 * @param matchFlags flags for matching control
 * @throws MatchException if any matching view was found
 */
@WorkerThread
@MatchingDsl
public inline fun <reified T : View> noMatch(
    noinline matchPredicate: MatchPredicate<T>,
    matchFlags: Set<MatchFlag> = MATCH_DEFAULT
) {
    val matches = matchViews(matchFlags, matchPredicate)

    if (matches.isNotEmpty()) {
        throw MatchFoundException("Found ${matches.size} views matching given criteria")
    }
}

/**
 * Ensures that no matching child view of a specified type exists in the
 * view hierarchy using given [matchPredicate].
 *
 * @param matchPredicate predicate describing which view should be considered a match
 * @throws MatchException if any matching view was found
 */
@WorkerThread
@MatchingDsl
public inline fun <reified T : View> View.noChildMatch(
    noinline matchPredicate: MatchPredicate<T>
) {
    val matches = matchChildViews(this, matchPredicate)

    if (matches.isNotEmpty()) {
        throw MatchFoundException("Found ${matches.size} child views matching given criteria")
    }
}