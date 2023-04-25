package pl.codesamurai.latte.core.hierarchy

import android.view.View
import android.view.WindowId
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class WindowRootTest {

    @Test
    fun `returns true when view's associated window is focused`() {
        val windowIdMock: WindowId = mock {
            on { isFocused } doReturn true
        }
        val view: View = mock {
            on { windowId } doReturn windowIdMock
        }
        val root = WindowRoot(view)

        val result = root.isActive

        assertTrue(result)
    }

    @Test
    fun `returns false when view's associated window is not focused`() {
        val windowIdMock: WindowId = mock {
            on { isFocused } doReturn false
        }
        val view: View = mock {
            on { windowId } doReturn windowIdMock
        }
        val root = WindowRoot(view)

        val result = root.isActive

        assertFalse(result)
    }

    @Test
    fun `returns root with content view`() {
        val contentView: View = mock()
        val view: View = mock {
            on { findViewById<View>(android.R.id.content) } doReturn contentView
        }
        val root = WindowRoot(view)

        val result = root.content

        assertNotNull(result)
        assertEquals(result.view, contentView)
    }

    @Test
    fun `returns null when root does not have a content view`() {
        val view: View = mock {
            on { findViewById<View>(android.R.id.content) } doReturn null
        }
        val root = WindowRoot(view)

        val result = root.content

        assertNull(result)
    }
}