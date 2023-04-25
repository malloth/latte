package com.test.latte.interactor

import android.os.SystemClock.sleep
import android.os.SystemClock.uptimeMillis
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_APP_SWITCH
import android.view.KeyEvent.KEYCODE_BACK
import android.view.KeyEvent.KEYCODE_CALL
import android.view.KeyEvent.KEYCODE_CAMERA
import android.view.KeyEvent.KEYCODE_ENDCALL
import android.view.KeyEvent.KEYCODE_HOME
import android.view.KeyEvent.KEYCODE_MENU
import android.view.KeyEvent.KEYCODE_POWER
import android.view.KeyEvent.KEYCODE_SOFT_LEFT
import android.view.KeyEvent.KEYCODE_SOFT_RIGHT
import android.view.KeyEvent.KEYCODE_VOLUME_DOWN
import android.view.KeyEvent.KEYCODE_VOLUME_UP
import android.view.ViewConfiguration.getPressedStateDuration
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

internal object UserInteractor : User {

    private const val DEFAULT_MULTI_PRESS_TIMEOUT = 300

    private val uiAutomation = getInstrumentation().uiAutomation

    override fun pressPower() {
        sendKeyEventSync(KEYCODE_POWER)
    }

    override fun pressVolumeUp() {
        sendKeyEventSync(KEYCODE_VOLUME_UP)
    }

    override fun pressVolumeDown() {
        sendKeyEventSync(KEYCODE_VOLUME_DOWN)
    }

    override fun pressCall() {
        sendKeyEventSync(KEYCODE_CALL)
    }

    override fun pressEndCall() {
        sendKeyEventSync(KEYCODE_ENDCALL)
    }

    override fun pressCamera() {
        sendKeyEventSync(KEYCODE_CAMERA)
    }

    override fun pressLeftSoftKey() {
        sendKeyEventSync(KEYCODE_SOFT_LEFT)
    }

    override fun pressRightSoftKey() {
        sendKeyEventSync(KEYCODE_SOFT_RIGHT)
    }

    override fun pressMenu() {
        sendKeyEventSync(KEYCODE_MENU)
    }

    override fun pressBack() {
        sendKeyEventSync(KEYCODE_BACK)
    }

    override fun pressHome() {
        sendKeyEventSync(KEYCODE_HOME)
    }

    override fun pressRecentApps() {
        sendKeyEventSync(KEYCODE_APP_SWITCH)
    }

    override fun pressKey(keyCode: Int) {
        sendKeyEventSync(keyCode)
    }

    override fun rotateDevice(rotation: Int) {
        uiAutomation.setRotation(rotation)
    }

    private fun sendKeyEventSync(keyCode: Int) {
        with(uiAutomation) {
            val startTime = uptimeMillis()
            val pressDuration = getPressedStateDuration()
            val multiPressDuration = DEFAULT_MULTI_PRESS_TIMEOUT

            if (injectInputEvent(downKeyEvent(keyCode, startTime), true)) {
                sleep(pressDuration.toLong())

                if (injectInputEvent(upKeyEvent(keyCode, startTime), true))
                    sleep(multiPressDuration.toLong())
            }
        }
    }

    private fun downKeyEvent(keyCode: Int, downTime: Long) =
        KeyEvent(downTime, uptimeMillis(), ACTION_DOWN, keyCode, 0)

    private fun upKeyEvent(keyCode: Int, downTime: Long) =
        KeyEvent(downTime, uptimeMillis(), ACTION_UP, keyCode, 0)
}