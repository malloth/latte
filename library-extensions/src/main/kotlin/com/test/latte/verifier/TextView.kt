@file:Suppress("unused")

package com.test.latte.verifier

import android.widget.TextView
import androidx.annotation.StringRes

/**
 * Checks is [TextView] has a given text.
 *
 * @param str text to check
 * @return true if [TextView] has a given text, false otherwise
 */
fun TextView.hasText(str: String): Boolean =
    text.toString() == str

/**
 * Checks is [TextView] has a given text.
 *
 * @param resId string resource id
 * @return true if [TextView] has a given text, false otherwise
 */
fun TextView.hasText(@StringRes resId: Int): Boolean =
    text.toString() == resources.getString(resId)