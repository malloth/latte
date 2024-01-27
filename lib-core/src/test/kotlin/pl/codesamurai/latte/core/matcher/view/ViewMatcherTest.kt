package pl.codesamurai.latte.core.matcher.view

import android.view.View
import org.mockito.kotlin.mock
import org.mockito.kotlin.only
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import pl.codesamurai.latte.core.matcher.MatchFlag
import pl.codesamurai.latte.core.matcher.MatchFlag.MATCH_ACTIVE_ROOTS
import pl.codesamurai.latte.core.matcher.MatchFlag.MATCH_CONTENT
import pl.codesamurai.latte.core.matcher.MatchPredicate
import pl.codesamurai.latte.core.matcher.view.hierarchy.Root
import pl.codesamurai.latte.core.matcher.view.hierarchy.RootProvider
import pl.codesamurai.latte.core.matcher.view.hierarchy.ViewTreeWalk
import pl.codesamurai.latte.core.matching.thread.checker.IdleChecker
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ViewMatcherTest {

    private val matchPredicateMock: MatchPredicate<View> = mock()
    private val viewMatcherMock: ViewMatcher = mock()
    private val viewMatcherFactory: (MatchPredicate<View>) -> ViewMatcher = { viewMatcherMock }
    private val viewTreeWalkMock: ViewTreeWalk = mock()
    private val idleCheckerMock: IdleChecker = mock()
    private val rootProviderMock: RootProvider = mock()

    @Test
    fun `waits until thread is idle`() {
        matchViews(
            emptySet(),
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            idleCheckerMock,
            rootProviderMock
        )

        verify(idleCheckerMock).waitUntilIdle()
    }

    @Test
    fun `returns empty list when there are no roots`() {
        whenever(rootProviderMock.roots).thenReturn(emptyList())

        val result = matchViews(
            emptySet(),
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            idleCheckerMock,
            rootProviderMock
        )

        assertTrue(result.isEmpty())
    }

    @Test
    fun `returns list of matching views when there are roots and no flags are set`() {
        val matchFlags = emptySet<MatchFlag>()
        val roots = listOf(root(), root(), root())
        val matches = listOf<View>(mock())

        whenever(rootProviderMock.roots).thenReturn(roots)
        whenever(viewTreeWalkMock.walk(roots[0].view, viewMatcherMock)).thenReturn(matches)

        val result = matchViews(
            matchFlags,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            idleCheckerMock,
            rootProviderMock
        )

        assertEquals(matches, result)
    }

    @Test
    fun `walks over all views of all roots when no flags are set`() {
        val matchFlags = emptySet<MatchFlag>()
        val roots = listOf(root(), root(), root())

        whenever(rootProviderMock.roots).thenReturn(roots)

        matchViews(
            matchFlags,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            idleCheckerMock,
            rootProviderMock
        )

        verify(viewTreeWalkMock).walk(roots[0].view, viewMatcherMock)
        verify(viewTreeWalkMock).walk(roots[1].view, viewMatcherMock)
        verify(viewTreeWalkMock).walk(roots[2].view, viewMatcherMock)
        verifyNoMoreInteractions(viewTreeWalkMock)
    }

    @Test
    fun `walks over all views of active roots when MATCH_ACTIVE_ROOTS flag is set`() {
        val matchFlags = setOf(MATCH_ACTIVE_ROOTS)
        val roots = listOf(root(isActive = true), root(), root())

        whenever(rootProviderMock.roots).thenReturn(roots)

        matchViews(
            matchFlags,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            idleCheckerMock,
            rootProviderMock
        )

        verify(viewTreeWalkMock, only()).walk(roots[0].view, viewMatcherMock)
    }

    @Test
    fun `walks over all content views of roots when MATCH_CONTENT flag is set`() {
        val matchFlags = setOf(MATCH_CONTENT)
        val contentRoot = root()
        val roots = listOf(root(content = contentRoot), root(), root())

        whenever(rootProviderMock.roots).thenReturn(roots)

        matchViews(
            matchFlags,
            matchPredicateMock,
            viewMatcherFactory,
            viewTreeWalkMock,
            idleCheckerMock,
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