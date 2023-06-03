package pl.codesamurai.latte.sample

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import pl.codesamurai.latte.core.ktx.interactor.performItemClick
import pl.codesamurai.latte.core.ktx.interactor.tap
import pl.codesamurai.latte.core.ktx.interactor.type
import pl.codesamurai.latte.core.ktx.interactor.user
import pl.codesamurai.latte.core.ktx.verifier.hasText
import pl.codesamurai.latte.core.matcher.match
import pl.codesamurai.latte.core.matcher.noMatch

class Test {

    private val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val rules: RuleChain = RuleChain
        .emptyRuleChain()
        .around(activityRule)

    @Test
    fun invalid_text_typing() {
        val editText1: (EditText) -> Boolean = { it.id == R.id.edit1 }

        match(editText1) {
            interact {
                tap()
                type("abc123")
            }
            verify(EditText::isFocused)
            verify {
                hasText("123")
            }
        }
    }

    @Test
    fun no_visible_edit_texts() {
        noMatch<EditText> {
            visibility == INVISIBLE || visibility == GONE
        }
    }

    @Test
    fun spinner_item_selection() {
        val spinner1: (Spinner) -> Boolean = { it.id == R.id.spinner1 }
        val spinnerItem5: (TextView) -> Boolean = { it.text == "Item 5" }

        match(spinner1) {
            verify {
                selectedItemPosition == 2
            }
            interact {
                tap()
            }
        }
        match(spinnerItem5) {
            interact {
                val parentView = parent as AdapterView<*>
                parentView.performItemClick(this)
            }
        }
        match(spinner1) {
            verify {
                selectedItemPosition == 4
            }
        }
    }

    @Test
    fun fragment_navigation_1() {
        val button1: (Button) -> Boolean = { it.id == R.id.button1 }
        val label: (TextView) -> Boolean = { it.hasText(R.string.label) }

        match(button1) {
            interact {
                tap()
            }
        }
        match(label)
        user {
            pressBack()
        }
        match(button1)
    }

    @Test
    fun fragment_navigation_2() {
        val button2: (Button) -> Boolean = { it.id == R.id.button2 }
        val label: (TextView) -> Boolean = { it.hasText(R.string.label) }

        match(button2) {
            interact {
                tap()
            }
        }
        match(label)
        noMatch(button2)
        user {
            pressBack()
        }
        match(button2)
    }
}