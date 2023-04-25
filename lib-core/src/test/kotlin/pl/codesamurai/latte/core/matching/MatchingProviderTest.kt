package pl.codesamurai.latte.core.matching

import android.view.View
import org.mockito.kotlin.mock
import pl.codesamurai.latte.core.matcher.MatchType.ALL
import pl.codesamurai.latte.core.matcher.MatchType.FIRST
import pl.codesamurai.latte.core.matcher.MatchType.SINGLE
import pl.codesamurai.latte.core.matcher.exception.MatchException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

internal class MatchingProviderTest {

    @Test
    fun `throws MatchException when no matches are found`() {
        val matches: List<View> = emptyList()

        assertFailsWith(MatchException::class) {
            matchingFor(matches)
        }
    }

    @Test
    fun `returns SingleMatching when one match is found and matchType is set to SINGLE`() {
        val matches: List<View> = listOf(mock())
        val matchType = SINGLE

        val result = matchingFor(matches, matchType)

        assertTrue(result is SingleMatching)
        assertEquals(result.view, matches[0])
    }

    @Test
    fun `throws MatchException when many matches are found and matchType is set to SINGLE`() {
        val matches: List<View> = listOf(mock(), mock(), mock())
        val matchType = SINGLE

        assertFailsWith(MatchException::class) {
            matchingFor(matches, matchType)
        }
    }

    @Test
    fun `returns SingleMatching when one match is found and matchType is set to FIRST`() {
        val matches: List<View> = listOf(mock())
        val matchType = FIRST

        val result = matchingFor(matches, matchType)

        assertTrue(result is SingleMatching)
        assertEquals(result.view, matches[0])
    }

    @Test
    fun `returns SingleMatching when many matches are found and matchType is set to FIRST`() {
        val matches: List<View> = listOf(mock(), mock(), mock())
        val matchType = FIRST

        val result = matchingFor(matches, matchType)

        assertTrue(result is SingleMatching)
        assertEquals(result.view, matches[0])
    }

    @Test
    fun `returns SingleMatching when one match is found and matchType is set to ALL`() {
        val matches: List<View> = listOf(mock())
        val matchType = ALL

        val result = matchingFor(matches, matchType)

        assertTrue(result is SingleMatching)
        assertEquals(result.view, matches[0])
    }

    @Test
    fun `returns MultipleMatching when many matches are found and matchType is set to ALL`() {
        val matches: List<View> = listOf(mock(), mock(), mock())
        val matchType = ALL

        val result = matchingFor(matches, matchType)

        assertTrue(result is MultipleMatching)
        assertEquals(result.views, matches)
    }

    @Test
    fun `throws MatchException when any matches are found`() {
        val matches: List<View> = listOf(mock(), mock(), mock())

        assertFailsWith(MatchException::class) {
            noMatchingFor(matches)
        }
    }

    @Test
    fun `does not throw MatchException when no matches are found`() {
        val matches: List<View> = emptyList()

        noMatchingFor(matches)
    }
}