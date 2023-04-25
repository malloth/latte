package com.test.latte.matcher

import android.widget.ListView
import android.widget.TextView
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.*
import org.junit.Test

class ViewMatcherFactoryTest {

    private val tested = ViewMatcherFactory

    @Test
    fun `creates a ViewMatcher`() {
        val matchPredicateMock: MatchPredicate<TextView> = mock()

        val viewMatcher = tested.create(matchPredicateMock)

        assertNotNull(viewMatcher)
    }

    @Test
    fun `created ViewMatcher does not use MatchPredicate when view is of invalid type`() {
        val view: ListView = mock()
        val matchPredicateMock: MatchPredicate<TextView> = mock()

        val viewMatcher = tested.create(matchPredicateMock)
        viewMatcher(view)

        verify(matchPredicateMock, never()).invoke(any())
    }

    @Test
    fun `created ViewMatcher uses MatchPredicate when view is of valid type`() {
        val view: TextView = mock()
        val matchPredicateMock: MatchPredicate<TextView> = mock {
            onGeneric { invoke(any()) } doReturn true
        }

        val viewMatcher = tested.create(matchPredicateMock)
        viewMatcher(view)

        verify(matchPredicateMock).invoke(view)
    }

    @Test
    fun `created ViewMatcher returns false when view is of invalid type`() {
        val view: ListView = mock()
        val matchPredicateMock: MatchPredicate<TextView> = mock()

        val viewMatcher = tested.create(matchPredicateMock)
        val result = viewMatcher(view)

        assertFalse(result)
    }

    @Test
    fun `created ViewMatcher returns false when view is of valid type and does not match the predicate`() {
        val view: TextView = mock()
        val matchPredicateMock: MatchPredicate<TextView> = mock {
            onGeneric { invoke(any()) } doReturn false
        }

        val viewMatcher = tested.create(matchPredicateMock)
        val result = viewMatcher(view)

        assertFalse(result)
    }

    @Test
    fun `created ViewMatcher returns true when view is of valid type and matches the predicate`() {
        val view: TextView = mock()
        val matchPredicateMock: MatchPredicate<TextView> = mock {
            onGeneric { invoke(any()) } doReturn true
        }

        val viewMatcher = tested.create(matchPredicateMock)
        val result = viewMatcher(view)

        assertTrue(result)
    }
}