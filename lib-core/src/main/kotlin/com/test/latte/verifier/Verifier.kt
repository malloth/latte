package com.test.latte.verifier

/**
 * Type alias for verifier predicate.
 */
public typealias Verifier<T> = T.() -> Comparable<Boolean>