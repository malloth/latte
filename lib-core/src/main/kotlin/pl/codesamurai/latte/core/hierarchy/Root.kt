package pl.codesamurai.latte.core.hierarchy

import android.view.View

@PublishedApi
internal interface Root {

    val isActive: Boolean

    val view: View

    val content: Root?
}