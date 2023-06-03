package pl.codesamurai.latte.core.matcher.view.hierarchy

import android.view.View

@PublishedApi
internal interface Root {

    val isActive: Boolean

    val view: View

    val content: Root?
}