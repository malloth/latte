package com.test.latte.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FlagsTest {

    @Test
    fun `returns true when contains a given flag`() {
        val flags = 0b00001011
        val flag  = 0b00000010

        val result = flags.hasFlags(flag)

        assertTrue(result)
    }

    @Test
    fun `returns false when does not contain a given flag`() {
        val flags = 0b00001011
        val flag  = 0b00000100

        val result = flags.hasFlags(flag)

        assertFalse(result)
    }
}