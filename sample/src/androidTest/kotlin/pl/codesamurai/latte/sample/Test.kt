package pl.codesamurai.latte.sample

import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import pl.codesamurai.latte.core.ktx.interactor.device
import pl.codesamurai.latte.core.ktx.interactor.performItemClick
import pl.codesamurai.latte.core.ktx.interactor.tap
import pl.codesamurai.latte.core.ktx.interactor.type
import pl.codesamurai.latte.core.ktx.verifier.hasText
import pl.codesamurai.latte.core.matcher.match
import pl.codesamurai.latte.core.matcher.noMatch
import pl.codesamurai.latte.core.verifier.verify

class Test {

    private val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val rules: RuleChain = RuleChain.outerRule(activityRule)

    @Test
    fun invalid_text_typing() {
        // given
        val editTextWithId: (EditText) -> Boolean = { it.id == R.id.edit1 }

        // when
        val matching = match(editTextWithId) {
            tap()
            type("abc123")
        }

        // then
        matching.verify {
            isFocused && hasText("123")
        }
    }

    @Test
    fun no_invisible_nor_gone_edit_texts() {
        // given
        val visibleEditText: (EditText) -> Boolean = { !it.isVisible }

        // then
        noMatch(visibleEditText)
    }

    @Test
    fun spinner_item_selection() {
        // given
        val spinnerWithId: (Spinner) -> Boolean = { it.id == R.id.spinner1 }
        val spinnerItemWithText: (TextView) -> Boolean = { it.text == "Item 5" }

        // when
        val matching = match(spinnerWithId) {
            verify {
                selectedItemPosition == 2
            }
            tap()
        }
        match(spinnerItemWithText) {
            (parent as AdapterView<*>).performItemClick(this)
        }

        // then
        matching.verify {
            selectedItemPosition == 4
        }
    }

    @Test
    fun fragment_navigation_1() {
        // given
        val buttonWithId: (Button) -> Boolean = { it.id == R.id.button1 }
        val labelWithText: (TextView) -> Boolean = { it.hasText(R.string.label) }

        // when
        match(buttonWithId) {
            tap()
        }
        match(labelWithText)
        device {
            pressBack()
        }

        // then
        match(buttonWithId)
    }

    @Test
    fun fragment_navigation_2() {
        // given
        val buttonWithId: (Button) -> Boolean = { it.id == R.id.button2 }
        val labelWithText: (TextView) -> Boolean = { it.hasText(R.string.label) }

        // when
        match(buttonWithId) {
            tap()
        }
        match(labelWithText)
        noMatch(buttonWithId)
        device {
            pressBack()
        }

        // then
        match(buttonWithId)
    }
}