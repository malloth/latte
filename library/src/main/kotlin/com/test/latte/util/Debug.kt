package com.test.latte.util

import android.view.View
import android.view.View.NO_ID

internal val View.debugId: String
    get() {
        return if (id != NO_ID) {
            if (id > 0 && resourceHasPackage(id) && resources != null) {
                val pkgName = when (id and -0x1000000) {
                    0x7f000000 -> "app"
                    0x01000000 -> "android"
                    else -> resources.getResourcePackageName(id)
                }
                val typeName = resources.getResourceTypeName(id)
                val entryName = resources.getResourceEntryName(id)

                "$pkgName:$typeName/$entryName"
            } else "#${Integer.toHexString(id)}"
        } else "null"
    }

private fun resourceHasPackage(resId: Int): Boolean = resId.ushr(24) != 0