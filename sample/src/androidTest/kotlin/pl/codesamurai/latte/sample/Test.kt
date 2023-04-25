@file:Suppress("DEPRECATION")

package pl.codesamurai.latte.sample

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.*
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import pl.codesamurai.latte.core.matcher.match
import pl.codesamurai.latte.core.matcher.noMatch
import pl.codesamurai.latte.core.verifier.orFail
import pl.codesamurai.latte.extensions.interactor.performItemClick
import pl.codesamurai.latte.extensions.interactor.typeText
import pl.codesamurai.latte.extensions.interactor.user
import pl.codesamurai.latte.extensions.verifier.hasText

class Test {

    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val rules: RuleChain = RuleChain
        .emptyRuleChain()
        .around(activityRule)

    @Test
    fun test1() {
        match<EditText> {
            id == R.id.edit1
        }.interact {
            performClick()
            typeText("abc123")
        }.verify {
            isFocused
        }.verify {
            hasText("123") orFail "EditText has text '$text' instead of '123'"
        }
    }

    @Test
    fun test2() {
        noMatch<EditText> {
            visibility == INVISIBLE || visibility == GONE
        }
    }

    @Test
    fun test3() {
        match<Spinner> {
            id == R.id.spinner1
        }.verify {
            (selectedItemPosition == 2) orFail "Spinner has selected position $selectedItemPosition instead of 2"
        }.interact {
            performClick()
        }

        match<TextView> {
            hasText("Item 5")
        }.interact {
            val parentView = parent as AdapterView<*>
            parentView.performItemClick(this)
        }

        match<Spinner> {
            id == R.id.spinner1
        }.verify {
            (selectedItemPosition == 4) orFail "Spinner has selected position $selectedItemPosition instead of 4"
        }
    }

    @Test
    fun test4() {
        match<Button> {
            id == R.id.button1
        }.interact {
            performClick()
        }

        match<TextView> {
            hasText(R.string.label)
        }

        user {
            pressBack()
        }

        match<Button> {
            id == R.id.button1
        }
    }

    @Test
    fun test5() {
        match<Button> {
            id == R.id.button2
        }.interact {
            performClick()
        }

        match<TextView> {
            hasText(R.string.label)
        }

        noMatch<Button> {
            id == R.id.button2
        }

        user {
            pressBack()
        }

        match<Button> {
            id == R.id.button2
        }
    }
}