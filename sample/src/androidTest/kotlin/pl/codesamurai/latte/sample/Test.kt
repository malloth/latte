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
    val rules: RuleChain = RuleChain.outerRule(activityRule)

    @Test
    fun invalid_text_typing() {
        val inputSelector: (EditText) -> Boolean = { it.id == R.id.edit1 }

        match(inputSelector) {
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
        val spinnerSelector: (Spinner) -> Boolean = { it.id == R.id.spinner1 }
        val spinnerItemSelector: (TextView) -> Boolean = { it.text == "Item 5" }

        match(spinnerSelector) {
            verify {
                selectedItemPosition == 2
            }
            interact {
                tap()
            }
        }
        match(spinnerItemSelector) {
            interact {
                val parentView = parent as AdapterView<*>
                parentView.performItemClick(this)
            }
        }
        match(spinnerSelector) {
            verify {
                selectedItemPosition == 4
            }
        }
    }

    @Test
    fun fragment_navigation_1() {
        val buttonSelector: (Button) -> Boolean = { it.id == R.id.button1 }
        val labelSelector: (TextView) -> Boolean = { it.hasText(R.string.label) }

        match(buttonSelector) {
            interact {
                tap()
            }
        }
        match(labelSelector)
        user {
            pressBack()
        }
        match(buttonSelector)
    }

    @Test
    fun fragment_navigation_2() {
        val buttonSelector: (Button) -> Boolean = { it.id == R.id.button2 }
        val labelSelector: (TextView) -> Boolean = { it.hasText(R.string.label) }

        match(buttonSelector) {
            interact {
                tap()
            }
        }
        match(labelSelector)
        noMatch(buttonSelector)
        user {
            pressBack()
        }
        match(buttonSelector)
    }
}