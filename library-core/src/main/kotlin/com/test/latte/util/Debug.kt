package com.test.latte.util

import android.view.View
import android.view.View.NO_ID

internal val View.debugId: String?
    get() {
        return if (id != NO_ID) {
            if (id > 0 && resourceHasPackage(id) && resources != null) {
                val pkgName = when (id and RES_OFFSET_MASK) {
                    RES_OFFSET_APP -> PACKAGE_APP
                    RES_OFFSET_ANDROID -> PACKAGE_ANDROID
                    else -> resources.getResourcePackageName(id)
                }
                val typeName = resources.getResourceTypeName(id)
                val entryName = resources.getResourceEntryName(id)

                "$pkgName:$typeName/$entryName"
            } else "#${id.toHexString()}"
        } else null
    }

private fun resourceHasPackage(resId: Int): Boolean = resId.ushr(24) != 0

private fun Int.toHexString(): String = Integer.toHexString(this)

private const val PACKAGE_ANDROID = "android"
private const val PACKAGE_APP = "app"

private const val RES_OFFSET_ANDROID = 0x01000000
private const val RES_OFFSET_APP = 0x7f000000
private const val RES_OFFSET_MASK = -0x1000000