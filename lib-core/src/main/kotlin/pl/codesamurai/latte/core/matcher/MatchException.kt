package pl.codesamurai.latte.core.matcher

/**
 * Exception throw when error occurs after view matching.
 *
 * @param message the detail message
 */
public sealed class MatchException(message: String) : RuntimeException(message) {

    public class NoMatchFoundException(message: String) : MatchException(message)

    public class MatchFoundException(message: String) : MatchException(message)

    public class MultipleMatchesFoundException(message: String) : MatchException(message)

    init {
        stackTrace = stackTrace.drop(1).toTypedArray()
    }
}