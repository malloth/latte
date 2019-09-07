package com.test.latte.matching

import android.widget.TextView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

class SingleMatchingTest {

    private val viewMock = mock<TextView>()
    private val currentThreadRunner: (() -> Any) -> Any = { it() }

    private val tested = SingleMatching(viewMock, currentThreadRunner)

    @Test
    fun `performs interactions on a view`() {
        val interactions: TextView.() -> Unit = mock()

        tested.interact(interactions)

        verify(interactions).invoke(viewMock)
        verifyNoMoreInteractions(interactions)
    }

    @Test
    fun `performs verifications on a view`() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(viewMock) } doReturn true
        }

        tested.verify(verifications)

        verify(verifications).invoke(viewMock)
        verifyNoMoreInteractions(verifications)
    }

    @Test(expected = AssertionError::class)
    fun `throws AssertionError when verification returns false`() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(viewMock) } doReturn false
        }

        tested.verify(verifications)
    }
}