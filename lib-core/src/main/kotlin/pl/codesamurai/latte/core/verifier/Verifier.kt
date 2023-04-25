package pl.codesamurai.latte.core.verifier

/**
 * Type alias for verifier predicate.
 */
public typealias Verifier<T> = T.() -> Comparable<Boolean>