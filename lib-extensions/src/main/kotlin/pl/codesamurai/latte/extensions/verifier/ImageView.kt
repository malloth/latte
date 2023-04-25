@file:Suppress("unused")

package pl.codesamurai.latte.extensions.verifier

import android.content.res.Resources.Theme
import android.widget.ImageView
import androidx.annotation.DrawableRes

/**
 * Checks if [ImageView] has a given drawable.
 *
 * @param resId drawable resource id
 * @param theme theme used by the drawable
 */
public fun ImageView.hasDrawable(
    @DrawableRes resId: Int,
    theme: Theme = context.theme
): Boolean = drawable == resources.getDrawable(resId, theme)