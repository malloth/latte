@file:Suppress("unused")

package com.test.latte.verifier

import android.content.res.Resources
import android.widget.ImageView
import androidx.annotation.DrawableRes

/**
 * Checks if [ImageView] has a given drawable.
 *
 * @param resId drawable resource id
 * @param theme theme used by the drawable
 */
fun ImageView.hasDrawable(@DrawableRes resId: Int, theme: Resources.Theme = context.theme): Boolean =
    drawable == resources.getDrawable(resId, theme)