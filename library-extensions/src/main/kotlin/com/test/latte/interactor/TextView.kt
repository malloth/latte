@file:Suppress("unused")

package com.test.latte.interactor

import android.view.KeyCharacterMap.VIRTUAL_KEYBOARD
import android.view.KeyCharacterMap.load
import android.widget.EditText

fun EditText.inputText(text: String) {
    val keyCharacterMap = load(VIRTUAL_KEYBOARD)
    val events = keyCharacterMap.getEvents(text.toCharArray())

    events?.forEach {
        it.dispatch(this, null, this)
    }
}