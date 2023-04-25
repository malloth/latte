package com.test.latte.verifier

/**
 * Structure describing a verification result.
 *
 * @param isSuccess whether verification was successful
 * @param failureDescription description of possible failure
 * @property isSuccess Verification state.
 * @property failureDescription Error description of failed verification.
 */
public data class VerificationResult(
    val isSuccess: Boolean,
    val failureDescription: String
) : Comparable<Boolean> {

    override fun compareTo(other: Boolean): Int =
        isSuccess.compareTo(other)
}