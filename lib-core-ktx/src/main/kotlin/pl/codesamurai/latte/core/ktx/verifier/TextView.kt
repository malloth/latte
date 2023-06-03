@file:Suppress("unused")

package pl.codesamurai.latte.core.ktx.verifier

import android.widget.TextView
import androidx.annotation.StringRes

/**
 * Checks if [TextView] has ellipsized text.
 *
 * @return true if [TextView] has ellipsized text, false otherwise
 */
public fun TextView.hasEllipsizedText(): Boolean =
    layout?.let { it.lineCount > 0 && it.getEllipsisCount(it.lineCount - 1) > 0 } ?: false

/**
 * Checks if [TextView] has multiline text.
 *
 * @return true if [TextView] has multiline text, false otherwise
 */
public fun TextView.hasMultilineText(): Boolean =
    lineCount > 1

/**
 * Checks if [TextView] has a given text.
 *
 * @param str text to check
 * @return true if [TextView] has a given text, false otherwise
 */
public fun TextView.hasText(str: String): Boolean =
    text.toString() == str

/**
 * Checks if [TextView] has a given text.
 *
 * @param resId string resource id
 * @return true if [TextView] has a given text, false otherwise
 */
public fun TextView.hasText(@StringRes resId: Int): Boolean =
    text.toString() == resources.getString(resId)