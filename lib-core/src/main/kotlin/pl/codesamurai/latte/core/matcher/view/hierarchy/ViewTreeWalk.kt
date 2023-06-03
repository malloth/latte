package pl.codesamurai.latte.core.matcher.view.hierarchy

import android.view.View

@PublishedApi
internal interface ViewTreeWalk {

    fun walk(view: View, matcher: (View) -> Boolean): List<View>

    companion object {

        const val TAG = "ViewTreeWalk"
    }
}