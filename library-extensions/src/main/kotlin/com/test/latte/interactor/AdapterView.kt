package com.test.latte.interactor

import android.view.View
import android.widget.Adapter
import android.widget.AdapterView

/**
 * Clicks given item in [AdapterView]'s instance.
 *
 * @param child a child view to be clicked
 */
fun <T : Adapter> AdapterView<T>.performItemClick(child: View) {
    val pos = getPositionForView(child)
    val id = getItemIdAtPosition(pos)

    performItemClick(child, pos, id)
}