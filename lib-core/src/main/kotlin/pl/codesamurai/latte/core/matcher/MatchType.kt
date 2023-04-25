package pl.codesamurai.latte.core.matcher

/**
 * Strategy describing maximum amount of matched views.
 */
public enum class MatchType {

    /**
     * Matches single view only.
     */
    SINGLE,

    /**
     * Matches only first view, ignoring other.
     */
    FIRST,

    /**
     * Matches all views.
     */
    ALL
}