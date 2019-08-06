package com.test.latte.matching

import android.view.View

interface Matching<T: View> {

    fun interact(interactor: T.() -> Unit): Matching<T>

    fun verify(message: String? = null, verifier: T.() -> Boolean): Matching<T>
}