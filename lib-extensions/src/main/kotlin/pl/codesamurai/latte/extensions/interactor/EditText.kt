@file:Suppress("unused")

package pl.codesamurai.latte.extensions.interactor

import android.view.KeyCharacterMap.VIRTUAL_KEYBOARD
import android.view.KeyCharacterMap.load
import android.widget.EditText

/**
 * Types given text into an [EditText] by using a
 * virtual keyboard, thus emulating user behaviour.
 *
 * @param text text to be typed
 */
public fun EditText.typeText(text: String) {
    val keyCharacterMap = load(VIRTUAL_KEYBOARD)
    val events = keyCharacterMap.getEvents(text.toCharArray())

    events?.forEach {
        it.dispatch(this, null, this)
    }
}