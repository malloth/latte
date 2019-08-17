package com.test.latte.hierarchy

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert.assertEquals
import org.junit.Test

class DepthFirstViewTreeWalkTest {

    private val context = getInstrumentation().context

    private val tested = DepthFirstViewTreeWalk

    @Test
    fun returnsRoot() {
        val root = FrameLayout(context).apply { id = 1 }
        val matchPredicate: (View) -> Boolean = { it.id == 1 }

        val result = tested.walk(root, matchPredicate)

        assertEquals(listOf(root), result)
    }

    @Test
    fun returnsSingleChildInRoot() {
        val root = FrameLayout(context)
        val child1 = TextView(context).apply { id = 1 }
        val child2 = TextView(context).apply { id = 2 }
        val child3 = TextView(context).apply { id = 3 }

        with(root) {
            addView(child1)
            addView(child2)
            addView(child3)
        }
        val matchPredicate: (View) -> Boolean = { it.id == 2 }

        val result = tested.walk(root, matchPredicate)

        assertEquals(listOf(child2), result)
    }

    @Test
    fun returnsMultipleChildrenInRoot() {
        val parent = FrameLayout(context)
        val child1 = TextView(context).apply { id = 1 }
        val child2 = TextView(context).apply { id = 2 }
        val child3 = TextView(context).apply { id = 3 }

        with(parent) {
            addView(child1)
            addView(child2)
            addView(child3)
        }
        val matchPredicate: (View) -> Boolean = { it.id > 1 }

        val result = tested.walk(parent, matchPredicate)

        assertEquals(listOf(child2, child3), result)
    }

    @Test
    fun returnsChildrenInDepthFirstOrder() {
        val root = FrameLayout(context)
        val child1 = TextView(context).apply { id = 1 }
        val parent1 = FrameLayout(context)
        val child3 = TextView(context).apply { id = 3 }
        val child2 = TextView(context).apply { id = 2 }

        with(parent1) {
            addView(child3)
        }
        with(root) {
            addView(child1)
            addView(parent1)
            addView(child2)
        }
        val matchPredicate: (View) -> Boolean = { it.id > 1 }

        val result = tested.walk(root, matchPredicate)

        assertEquals(listOf(child3, child2), result)
    }
}