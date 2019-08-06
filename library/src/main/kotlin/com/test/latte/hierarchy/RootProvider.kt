package com.test.latte.hierarchy

import android.annotation.SuppressLint
import android.view.View
import java.lang.reflect.Field

@PublishedApi
internal object RootProvider {
    private const val WINDOW_MANAGER_GLOBAL_CLASS = "android.view.WindowManagerGlobal"
    private const val WINDOW_MANAGER_GLOBAL_GET_INSTANCE = "getInstance"
    private const val WINDOW_MANAGER_GLOBAL_VIEWS = "mViews"

    private val windowManagerGlobal: Any
    private val viewsField: Field

    init {
        @SuppressLint("PrivateApi")
        val cls = Class.forName(WINDOW_MANAGER_GLOBAL_CLASS)

        windowManagerGlobal = cls.getMethod(WINDOW_MANAGER_GLOBAL_GET_INSTANCE)
            .invoke(null)
        viewsField = cls.getDeclaredField(WINDOW_MANAGER_GLOBAL_VIEWS)
            .apply { isAccessible = true }
    }

    @Suppress("UNCHECKED_CAST")
    val roots: List<View>
        get() = viewsField.get(windowManagerGlobal) as List<View>
}