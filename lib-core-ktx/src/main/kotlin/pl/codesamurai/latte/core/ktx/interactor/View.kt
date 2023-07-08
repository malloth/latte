package pl.codesamurai.latte.core.ktx.interactor

import android.os.SystemClock.uptimeMillis
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.ViewConfiguration.getPressedStateDuration

/**
 * Presses given view.
 *
 * @param x horizontal coordinate of a tap
 * @param y vertical coordinate of a tap
 * @param duration time between down and up event
 */
public fun View.tap(
    x: Float = width / 2F,
    y: Float = height / 2F,
    duration: Int = getPressedStateDuration()
) {
    val now = uptimeMillis()
    val eventDown = MotionEvent.obtain(now, now, ACTION_DOWN, x, y, 0)
    val eventUp = MotionEvent.obtain(now, now + duration, ACTION_UP, x, y, 0)

    try {
        dispatchTouchEvent(eventDown)
        dispatchTouchEvent(eventUp)
    } finally {
        eventDown.recycle()
        eventUp.recycle()
    }
}