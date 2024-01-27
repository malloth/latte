package pl.codesamurai.latte.core.matching

import android.view.View

/**
 * Class containing matching views of a specified type.
 *
 * @property matches list of matching views
 */
public data class Matching<T : View>(val matches: List<T>)