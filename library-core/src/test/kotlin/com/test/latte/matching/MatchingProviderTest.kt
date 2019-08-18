package com.test.latte.matching

import android.view.View
import com.nhaarman.mockitokotlin2.mock
import com.test.latte.matcher.MatchType.*
import com.test.latte.matcher.exception.MatchException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MatchingProviderTest {

    @Test(expected = MatchException::class)
    fun `throws MatchException when no matches are found`() {
        val matches: List<View> = emptyList()

        matchingFor(matches)
    }

    @Test
    fun `returns SingleMatching when one match is found and matchType is set to SINGLE`() {
        val matches: List<View> = listOf(mock())
        val matchType = SINGLE

        val result = matchingFor(matches, matchType)

        assertTrue(result is SingleMatching)
        assertEquals((result as SingleMatching).view, matches[0])
    }

    @Test(expected = MatchException::class)
    fun `throws MatchException when many matches are found and matchType is set to SINGLE`() {
        val matches: List<View> = listOf(mock(), mock(), mock())
        val matchType = SINGLE

        matchingFor(matches, matchType)
    }

    @Test
    fun `returns SingleMatching when one match is found and matchType is set to FIRST`() {
        val matches: List<View> = listOf(mock())
        val matchType = FIRST

        val result = matchingFor(matches, matchType)

        assertTrue(result is SingleMatching)
        assertEquals((result as SingleMatching).view, matches[0])
    }

    @Test
    fun `returns SingleMatching when many matches are found and matchType is set to FIRST`() {
        val matches: List<View> = listOf(mock(), mock(), mock())
        val matchType = FIRST

        val result = matchingFor(matches, matchType)

        assertTrue(result is SingleMatching)
        assertEquals((result as SingleMatching).view, matches[0])
    }

    @Test
    fun `returns SingleMatching when one match is found and matchType is set to ALL`() {
        val matches: List<View> = listOf(mock())
        val matchType = ALL

        val result = matchingFor(matches, matchType)

        assertTrue(result is SingleMatching)
        assertEquals((result as SingleMatching).view, matches[0])
    }

    @Test
    fun `returns MultipleMatching when many matches are found and matchType is set to ALL`() {
        val matches: List<View> = listOf(mock(), mock(), mock())
        val matchType = ALL

        val result = matchingFor(matches, matchType)

        assertTrue(result is MultipleMatching)
        assertEquals((result as MultipleMatching).views, matches)
    }

    @Test(expected = MatchException::class)
    fun `throws MatchException when any matches are found`() {
        val matches: List<View> = listOf(mock(), mock(), mock())

        noMatchingFor(matches)
    }

    @Test
    fun `does not throw MatchException when no matches are found`() {
        val matches: List<View> = emptyList()

        noMatchingFor(matches)
    }
}