package com.test.latte.matching

import android.widget.TextView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

class SingleMatchingTest {

    private val viewMock = mock<TextView>()
    private val currentThreadRunner: (() -> Boolean) -> Boolean = { it() }

    private val tested = SingleMatching(viewMock, currentThreadRunner)

    @Test
    fun runsInteractions() {
        val interactions: TextView.() -> Unit = mock()

        tested.interact(interactions)

        verify(interactions).invoke(viewMock)
        verifyNoMoreInteractions(interactions)
    }

    @Test
    fun runsVerifications() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(viewMock) } doReturn true
        }

        tested.verify(null, verifications)

        verify(verifications).invoke(viewMock)
        verifyNoMoreInteractions(verifications)
    }

    @Test(expected = AssertionError::class)
    fun throwsAssertionErrorWhenVerificationReturnsFalse() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(viewMock) } doReturn false
        }

        tested.verify(null, verifications)
    }
}