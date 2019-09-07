package com.test.latte.matching

import android.widget.TextView
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

class MultipleMatchingTest {

    private val viewMocks: List<TextView> = listOf(mock(), mock(), mock())
    private val currentThreadRunner: (() -> Unit) -> Unit = { it() }

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

    @Test(expected = AssertionError::class)
    fun `throws AssertionError when verification on any view returns false`() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(any()) } doReturn false
        }

        tested.verify(verifications)
    }
}