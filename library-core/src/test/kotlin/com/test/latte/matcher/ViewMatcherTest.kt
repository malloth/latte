package com.test.latte.matcher

import android.view.View
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.test.latte.hierarchy.Root
import com.test.latte.hierarchy.RootProvider
import com.test.latte.hierarchy.ViewTreeWalk
import com.test.latte.thread.ThreadObserver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ViewMatcherTest {

    private val matchPredicateMock: MatchPredicate<View> = mock()
    private val viewMatcherMock: ViewMatcher = mock()
    private val viewMatcherFactory: (MatchPredicate<View>) -> ViewMatcher = { viewMatcherMock }
    private val viewTreeWalkMock: ViewTreeWalk = mock()
    private val threadObserverMock: ThreadObserver = mock()
    private val rootProviderMock: RootProvider = mock()

    @Test
    fun `waits until thread is idle`() {
        matchViews(
            0,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            threadObserverMock,
            rootProviderMock
        )

        verify(threadObserverMock).waitUntilIdle()
    }

    @Test
    fun `returns empty list when there are no roots`() {
        whenever(rootProviderMock.roots).thenReturn(emptyList())

        val result = matchViews(
            0,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            threadObserverMock,
            rootProviderMock
        )

        assertTrue(result.isEmpty())
    }

    @Test
    fun `returns list of matching views when there are roots and no flags are set`() {
        val matchFlags = 0
        val roots = listOf(root(), root(), root())
        val matches = listOf<View>(mock())

        whenever(rootProviderMock.roots).thenReturn(roots)
        whenever(viewTreeWalkMock.walk(roots[0].view, viewMatcherMock)).thenReturn(matches)

        val result = matchViews(
            matchFlags,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            threadObserverMock,
            rootProviderMock
        )

        assertEquals(matches, result)
    }

    @Test
    fun `walks over all views of all roots when no flags are set`() {
        val matchFlags = 0
        val roots = listOf(root(), root(), root())

        whenever(rootProviderMock.roots).thenReturn(roots)

        matchViews(
            matchFlags,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            threadObserverMock,
            rootProviderMock
        )

        verify(viewTreeWalkMock).walk(roots[0].view, viewMatcherMock)
        verify(viewTreeWalkMock).walk(roots[1].view, viewMatcherMock)
        verify(viewTreeWalkMock).walk(roots[2].view, viewMatcherMock)
        verifyNoMoreInteractions(viewTreeWalkMock)
    }

    @Test
    fun `walks over all views of active roots when MATCH_ACTIVE_ROOTS flag is set`() {
        val matchFlags = MATCH_ACTIVE_ROOTS
        val roots = listOf(root(isActive = true), root(), root())

        whenever(rootProviderMock.roots).thenReturn(roots)

        matchViews(
            matchFlags,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            threadObserverMock,
            rootProviderMock
        )

        verify(viewTreeWalkMock).walk(roots[0].view, viewMatcherMock)
        verifyNoMoreInteractions(viewTreeWalkMock)
    }

    @Test
    fun `walks over all content views of roots when MATCH_CONTENT flag is set`() {
        val matchFlags = MATCH_CONTENT
        val contentRoot = root()
        val roots = listOf(root(content = contentRoot), root(), root())

        whenever(rootProviderMock.roots).thenReturn(roots)

        matchViews(
            matchFlags,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            threadObserverMock,
            rootProviderMock
        )

        verify(viewTreeWalkMock).walk(contentRoot.view, viewMatcherMock)
        verify(viewTreeWalkMock).walk(roots[1].view, viewMatcherMock)
        verify(viewTreeWalkMock).walk(roots[2].view, viewMatcherMock)
        verifyNoMoreInteractions(viewTreeWalkMock)
    }

    private fun root(
        view: View = mock(),
        content: Root? = null,
        isActive: Boolean = false
    ): Root = object : Root {

        override val isActive = isActive

        override val view = view

        override val content: Root? = content
    }
}