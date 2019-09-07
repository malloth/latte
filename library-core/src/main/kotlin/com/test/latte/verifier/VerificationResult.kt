package com.test.latte.verifier

import com.test.latte.matching.Matching

/**
 * Type alias for verifier predicate returning [Boolean] as a result.
 */
typealias SimpleVerifier<T> = T.() -> Boolean

/**
 * Type alias for verifier predicate returning [VerificationResult] as a result.
 */
typealias ResultiveVerifier<T> = T.() -> VerificationResult

/**
 * Structure describing a verification result.
 *
 * @param isSuccess whether verification was successful
 * @param failureDescription description of possible failure
 * @property isSuccess Verification state.
 * @property failureDescription Error description of failed verification.
 */
data class VerificationResult(
    val isSuccess: Boolean,
    val failureDescription: String
)

/**
 * Creates a [VerificationResult] from a [Boolean] result.
 * It should be used inside [Matching.verifyWithResult] method.
 */
infix fun Boolean.orFail(failureDescription: String): VerificationResult =
    VerificationResult(this, failureDescription)