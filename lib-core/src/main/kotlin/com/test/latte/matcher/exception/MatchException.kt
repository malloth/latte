package com.test.latte.matcher.exception

/**
 * Exception throw when error occurs after view matching.
 *
 * @param message the detail message
 */
public class MatchException(message: String) : RuntimeException(message)