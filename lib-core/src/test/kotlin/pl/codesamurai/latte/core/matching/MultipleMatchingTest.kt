package pl.codesamurai.latte.core.matching

import android.widget.TextView
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class MultipleMatchingTest {

    private val viewMocks: List<TextView> = listOf(mock(), mock(), mock())
    private val currentThreadRunner: (() -> Comparable<Boolean>) -> Comparable<Boolean> = { it() }

    private val tested = MultipleMatching(viewMocks, currentThreadRunner)

    @Test
    fun `performs interactions on all views`() {
        val interactions: TextView.() -> Unit = mock()

        tested.interact(interactions)

        verify(interactions).invoke(viewMocks[0])
        verify(interactions).invoke(viewMocks[1])
        verify(interactions).invoke(viewMocks[2])
        verifyNoMoreInteractions(interactions)
    }

    @Test
    fun `performs verifications on all views`() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(any()) } doReturn true
        }

        tested.verify(verifications)

        verify(verifications).invoke(viewMocks[0])
        verify(verifications).invoke(viewMocks[1])
        verify(verifications).invoke(viewMocks[2])
        verifyNoMoreInteractions(verifications)
    }

    @Test
    fun `throws AssertionError when verification on any view returns false`() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(any()) } doReturn false
        }

        assertFailsWith(AssertionError::class) {
            tested.verify(verifications)
        }
    }
}