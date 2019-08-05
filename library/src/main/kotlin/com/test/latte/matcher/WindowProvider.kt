package com.test.latte.matcher

import android.view.Window
import androidx.test.runner.lifecycle.Stage

@PublishedApi
internal interface WindowProvider {

    fun getWindows(stage: Stage): List<Window>
}