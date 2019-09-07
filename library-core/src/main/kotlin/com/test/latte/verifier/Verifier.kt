package com.test.latte.verifier

/**
 * Type alias for verifier predicate.
 */
typealias Verifier<T> = T.() -> Comparable<Boolean>