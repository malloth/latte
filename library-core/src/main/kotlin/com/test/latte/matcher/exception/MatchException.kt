package com.test.latte.matcher.exception

/**
 * Exception throw when error occurs after view matching.
 *
 * @param message the detail message
 */
class MatchException(message: String) : RuntimeException(message)