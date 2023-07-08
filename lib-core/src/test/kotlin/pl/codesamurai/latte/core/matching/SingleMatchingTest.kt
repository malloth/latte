package pl.codesamurai.latte.core.matching

import android.widget.TextView
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class SingleMatchingTest {

    private val viewMock = mock<TextView>()
    private val currentThreadRunner: (() -> Boolean) -> Boolean = { it() }

    private val tested = SingleMatching(viewMock, currentThreadRunner)

    @Test
    fun `performs interactions on a view`() {
        val interactions: TextView.() -> Unit = mock()

        tested.interact(interactions)

        verify(interactions, only()).invoke(viewMock)
    }

    @Test
    fun `performs verifications on a view`() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(viewMock) } doReturn true
        }

        tested.verify(verifications)

        verify(verifications, only()).invoke(viewMock)
    }

    @Test
    fun `throws AssertionError when verification returns false`() {
        val verifications: TextView.() -> Boolean = mock {
            onGeneric { invoke(viewMock) } doReturn false
        }

        assertFailsWith(AssertionError::class) {
            tested.verify(verifications)
        }
    }
}