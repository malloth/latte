package pl.codesamurai.latte.extensions.interactor

import android.view.View
import android.widget.Adapter
import android.widget.AdapterView

/**
 * Clicks given item in [AdapterView]'s instance.
 *
 * @param child a child view to be clicked
 */
public fun <T : Adapter> AdapterView<T>.performItemClick(child: View) {
    val pos = getPositionForView(child)
    val id = getItemIdAtPosition(pos)

    performItemClick(child, pos, id)
}