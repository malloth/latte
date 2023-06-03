package pl.codesamurai.latte.core.matcher.view.hierarchy

import android.annotation.SuppressLint
import android.view.View
import java.lang.reflect.Field

@SuppressLint("PrivateApi", "DiscouragedPrivateApi")
@PublishedApi
internal object WindowRootProvider : RootProvider {
    private const val WINDOW_MANAGER_GLOBAL_CLASS = "android.view.WindowManagerGlobal"
    private const val WINDOW_MANAGER_GLOBAL_GET_INSTANCE = "getInstance"
    private const val WINDOW_MANAGER_GLOBAL_VIEWS = "mViews"

    private val windowManagerGlobal: Any?
    private val viewsField: Field?

    init {
        val cls = Class.forName(WINDOW_MANAGER_GLOBAL_CLASS)

        windowManagerGlobal = cls.getMethod(WINDOW_MANAGER_GLOBAL_GET_INSTANCE)
            .invoke(null)
        viewsField = cls.getDeclaredField(WINDOW_MANAGER_GLOBAL_VIEWS)
            .apply { isAccessible = true }
    }

    @Suppress("UNCHECKED_CAST")
    override val roots: List<Root>
        get() = (viewsField?.get(windowManagerGlobal) as List<View>?)
            .orEmpty()
            .map { WindowRoot(it) }
}