package com.test.latte.util

import android.annotation.SuppressLint
import android.view.View
import android.view.WindowManager.LayoutParams
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
@SuppressLint("PrivateApi")
@PublishedApi
internal object RootProvider {
    private const val WINDOW_MANAGER_GLOBAL_CLASS = "android.view.WindowManagerGlobal"
    private const val WINDOW_MANAGER_GLOBAL_GET_INSTANCE = "getInstance"
    private const val WINDOW_MANAGER_GLOBAL_VIEWS = "mViews"
    private const val WINDOW_MANAGER_GLOBAL_PARAMS = "mParams"

    private val windowManagerGlobal: Any
    private val viewsField: Field
    private val paramsField: Field

    init {
        val cls = Class.forName(WINDOW_MANAGER_GLOBAL_CLASS)

        windowManagerGlobal = cls.getMethod(WINDOW_MANAGER_GLOBAL_GET_INSTANCE)
            .invoke(null)
        viewsField = cls.getDeclaredField(WINDOW_MANAGER_GLOBAL_VIEWS)
            .apply { isAccessible = true }
        paramsField = cls.getDeclaredField(WINDOW_MANAGER_GLOBAL_PARAMS)
            .apply { isAccessible = true }
    }

    val roots: List<Root>
        get() {
            val views = viewsField.get(windowManagerGlobal) as List<View>
            val params = paramsField.get(windowManagerGlobal) as List<LayoutParams>

            return views.zip(params)
                .map { Root(it.first, it.second) }
                .reversed()
        }
}

@PublishedApi
internal data class Root(val view: View, val layoutParams: LayoutParams)