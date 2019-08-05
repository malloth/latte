package com.test.lattesample

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.*
import androidx.test.rule.ActivityTestRule
import com.test.latte.interactor.inputText
import com.test.latte.matcher.match
import com.test.latte.matcher.noMatch
import com.test.latte.verifier.hasText
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
            inputText("123")
        }.verify("EditText is not focused") {
            isFocused
        }.verify("EditText does not have text '123'") {
            hasText("123")
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
        }.verify("Spinner does not have selected position 2") {
            selectedItemPosition == 2
        }.interact {
            performClick()
        }

        match<TextView> {
            hasText("Item 5")
        }.interact {
            val parentView = parent as AdapterView<*>
            val pos = parentView.getPositionForView(this)
            val id = parentView.getItemIdAtPosition(pos)

            parentView.performItemClick(this, pos, id)
        }

        match<Spinner> {
            id == R.id.spinner1
        }.verify("Spinner does not have selected position 4") {
            selectedItemPosition == 4
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
    }
}