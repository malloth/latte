package pl.codesamurai.latte.core.matcher.view.hierarchy

import android.view.View

@PublishedApi
internal class WindowRoot(override val view: View) : Root {

    override val isActive: Boolean
        get() = view.windowId?.isFocused ?: false

    override val content: Root?
        get() = view.findViewById<View>(android.R.id.content)?.let { WindowRoot(it) }
}