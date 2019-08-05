@file:Suppress("unused")

package com.test.latte.verifier

import android.widget.TextView
import androidx.annotation.StringRes

fun TextView.hasText(str: String): Boolean =
    text.toString() == str

fun TextView.hasText(@StringRes resId: Int): Boolean =
    text.toString() == resources.getString(resId)