package com.test.latte.matcher

import android.widget.ListView
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.*
import org.junit.Test

class ViewMatcherFactoryTest {

    private val context = getInstrumentation().context

    private val tested = ViewMatcherFactory

    @Test
    fun createsViewMatcher() {
        val matchPredicateMock: MatchPredicate<TextView> = mock()

        val viewMatcher = tested.create(matchPredicateMock)

        assertNotNull(viewMatcher)
    }

    @Test
    fun createdViewMatcherDoesNotUseMatchPredicateWhenViewIsOfInvalidType() {
        val view = ListView(context)
        val matchPredicateMock: MatchPredicate<TextView> = mock()

        val viewMatcher = tested.create(matchPredicateMock)
        viewMatcher(view)

        verify(matchPredicateMock, never()).invoke(any())
    }

    @Test
    fun createdViewMatcherUsesMatchPredicateWhenViewIsOfValidType() {
        val view = TextView(context)
        val matchPredicateMock: MatchPredicate<TextView> = mock {
            onGeneric { invoke(any()) } doReturn true
        }

        val viewMatcher = tested.create(matchPredicateMock)
        viewMatcher(view)

        verify(matchPredicateMock).invoke(view)
    }

    @Test
    fun createdViewMatcherReturnsFalseWhenViewIsOfInvalidType() {
        val view = ListView(context)
        val matchPredicateMock: MatchPredicate<TextView> = mock()

        val viewMatcher = tested.create(matchPredicateMock)
        val result = viewMatcher(view)

        assertFalse(result)
    }

    @Test
    fun createdViewMatcherReturnsFalseWhenViewIsOfValidTypeAndDoesNotMatchThePredicate() {
        val view = TextView(context)
        val matchPredicateMock: MatchPredicate<TextView> = mock {
            onGeneric { invoke(any()) } doReturn false
        }

        val viewMatcher = tested.create(matchPredicateMock)
        val result = viewMatcher(view)

        assertFalse(result)
    }

    @Test
    fun createdViewMatcherReturnsTrueWhenViewIsOfValidTypeAndMatchesThePredicate() {
        val view = TextView(context)
        val matchPredicateMock: MatchPredicate<TextView> = mock {
            onGeneric { invoke(any()) } doReturn true
        }

        val viewMatcher = tested.create(matchPredicateMock)
        val result = viewMatcher(view)

        assertTrue(result)
    }
}