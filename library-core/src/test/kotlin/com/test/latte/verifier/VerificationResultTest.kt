package com.test.latte.verifier

import org.junit.Assert.assertEquals
import org.junit.Test

class VerificationResultTest {

    @Test
    fun `creates a result when verification is successful`() {
        val isSuccess = true
        val failureDescription = "failureDescription"

        val result = isSuccess orFail failureDescription

        assertEquals(isSuccess, result.isSuccess)
        assertEquals(failureDescription, result.failureDescription)
    }

    @Test
    fun `creates a result when verification is not successful`() {
        val isSuccess = false
        val failureDescription = "failureDescription"

        val result = isSuccess orFail failureDescription

        assertEquals(isSuccess, result.isSuccess)
        assertEquals(failureDescription, result.failureDescription)
    }
}