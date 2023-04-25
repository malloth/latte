package com.test.latte.util

import org.junit.Assert.assertEquals
import org.junit.Test

class CollectionsTest {

    @Test
    fun `filters items when condition is met`() {
        val list = listOf(1, 2, 3, 4, 5)
        val condition = true
        val predicate: (Int) -> Boolean = { it > 3 }

        val results = list.filterIf(condition, predicate)

        assertEquals(listOf(4, 5), results)
    }

    @Test
    fun `does not filter items when condition is not met`() {
        val list = listOf(1, 2, 3, 4, 5)
        val condition = false
        val predicate: (Int) -> Boolean = { it > 3 }

        val results = list.filterIf(condition, predicate)

        assertEquals(list, results)
    }

    @Test
    fun `maps items when condition is met`() {
        val list = listOf(1, 2, 3, 4, 5)
        val condition = true
        val transformation: (Int) -> Int = { it * 2 }

        val results = list.mapIf(condition, transformation)

        assertEquals(listOf(2, 4, 6, 8, 10), results)
    }

    @Test
    fun `does not map items when condition is not met`() {
        val list = listOf(1, 2, 3, 4, 5)
        val condition = false
        val transformation: (Int) -> Int = { it * 2 }

        val results = list.mapIf(condition, transformation)

        assertEquals(list, results)
    }
}