package pl.codesamurai.latte.core.matcher.view

import android.widget.ListView
import android.widget.TextView
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import pl.codesamurai.latte.core.matcher.MatchPredicate
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class ViewMatcherFactoryTest {

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