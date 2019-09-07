package com.test.lattesample

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.*
import androidx.test.rule.ActivityTestRule
import com.test.latte.interactor.performItemClick
import com.test.latte.interactor.typeText
import com.test.latte.interactor.user
import com.test.latte.matcher.match
import com.test.latte.matcher.noMatch
import com.test.latte.verifier.hasText
import com.test.latte.verifier.orFail
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

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
        }.verify{
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