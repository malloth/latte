package pl.codesamurai.latte.core.matcher

/**
 * Exception throw when error occurs after view matching.
 *
 * @param message the detail message
 */
public class MatchException(message: String) : RuntimeException(message)