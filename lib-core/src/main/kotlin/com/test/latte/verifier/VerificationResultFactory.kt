package com.test.latte.verifier

import com.test.latte.matching.Matching

/**
 * Creates a [VerificationResult] from a [Boolean] result.
 *
 * It is used inside [verify()][Matching.verify] method to
 * customize thrown assertion exceptions.
 */
public infix fun Boolean.orFail(failureDescription: String): VerificationResult =
    VerificationResult(this, failureDescription)