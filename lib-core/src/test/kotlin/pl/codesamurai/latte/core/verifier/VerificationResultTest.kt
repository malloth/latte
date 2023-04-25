package pl.codesamurai.latte.core.verifier

import kotlin.test.Test
import kotlin.test.assertEquals

internal class VerificationResultTest {

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