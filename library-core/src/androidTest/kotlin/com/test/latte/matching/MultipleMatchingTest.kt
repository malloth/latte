package com.test.latte.matching

import android.widget.TextView
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

class MultipleMatchingTest {

    private val viewMocks: List<TextView> = listOf(mock(), mock(), mock())
    private val currentThreadRunner: (() -> Unit) -> Unit = { it() }

    private val tested = MultipleMatching(viewMocks, currentThreadRunner)

    @Test
    fun runsInteractions() {
        val interactions: TextView.() -> Unit = mock()

        tested.interact(interactions)

        verify(interactions).invoke(viewMocks[0])
        verify(interactions).invoke(viewMocks[1])
        verify(interactions).invoke(viewMocks[2])
        verifyNoMoreInteractions(interactions)
    }

    @Test
    fun runsVerifications() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(any()) } doReturn true
        }

        tested.verify(null, verifications)

        verify(verifications).invoke(viewMocks[0])
        verify(verifications).invoke(viewMocks[1])
        verify(verifications).invoke(viewMocks[2])
        verifyNoMoreInteractions(verifications)
    }

    @Test(expected = AssertionError::class)
    fun throwsAssertionErrorWhenVerificationReturnsFalse() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(any()) } doReturn false
        }

        tested.verify(null, verifications)
    }
}