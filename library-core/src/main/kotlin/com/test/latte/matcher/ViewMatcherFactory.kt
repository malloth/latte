package com.test.latte.matcher

import android.view.View

@PublishedApi
internal object ViewMatcherFactory {

    inline fun <reified T : View> create(noinline predicate: T.() -> Boolean): (View) -> Boolean =
        { it is T && predicate(it) }
}