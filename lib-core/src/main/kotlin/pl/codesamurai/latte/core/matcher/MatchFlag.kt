package pl.codesamurai.latte.core.matcher

/**
 * Strategy describing matching behaviour.
 */
public enum class MatchFlag {

    /**
     * Flag for matching views only inside active windows (activities, dialogs, popups).
     */
    MATCH_ACTIVE_ROOTS,

    /**
     * Flag for matching views inside content view.
     */
    MATCH_CONTENT;

    public companion object {

        /**
         * Set of flags specifying default matching behaviour.
         */
        public val MATCH_DEFAULT: Set<MatchFlag> = setOf(MATCH_ACTIVE_ROOTS, MATCH_CONTENT)
    }
}