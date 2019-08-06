@file:Suppress("unused")

package com.test.latte.verifier

import android.content.res.Resources
import android.widget.ImageView

fun ImageView.hasDrawable(resId: Int, theme: Resources.Theme = context.theme): Boolean =
    drawable == resources.getDrawable(resId, theme)