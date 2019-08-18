package com.test.latte.interactor

import android.view.Surface
import androidx.annotation.WorkerThread

/**
 * Interface describing basic user interactions.
 */
interface User {

    /**
     * Emulates user pressing power button.
     */
    fun pressPower()

    /**
     * Emulates user pressing volume up button.
     */
    fun pressVolumeUp()

    /**
     * Emulates user pressing volume down button.
     */
    fun pressVolumeDown()

    /**
     * Emulates user pressing call button.
     */
    fun pressCall()

    /**
     * Emulates user pressing end call button.
     */
    fun pressEndCall()

    /**
     * Emulates user pressing camera button.
     */
    fun pressCamera()

    /**
     * Emulates user pressing left soft key button.
     */
    fun pressLeftSoftKey()

    /**
     * Emulates user pressing right soft key button.
     */
    fun pressRightSoftKey()

    /**
     * Emulates user pressing menu button.
     */
    fun pressMenu()

    /**
     * Emulates user pressing back button.
     */
    fun pressBack()

    /**
     * Emulates user pressing home button.
     */
    fun pressHome()

    /**
     * Emulates user pressing recent apps button.
     */
    fun pressRecentApps()

    /**
     * Emulates user pressing given button.
     *
     * @param keyCode key code of a button
     */
    fun pressKey(keyCode: Int)

    /**
     * Emulates user rotating a device.
     *
     * @param rotation one of [ROTATION_0], [ROTATION_90], [ROTATION_180], [ROTATION_270]
     */
    fun rotateDevice(rotation: Int)

    companion object {

        /**
         * Rotation angle 0 degrees (natural).
         */
        const val ROTATION_0 = Surface.ROTATION_0
        /**
         * Rotation angle 90 degrees.
         */
        const val ROTATION_90 = Surface.ROTATION_90
        /**
         * Rotation angle 180 degrees.
         */
        const val ROTATION_180 = Surface.ROTATION_180
        /**
         * Rotation angle 270 degrees.
         */
        const val ROTATION_270 = Surface.ROTATION_270
    }
}

/**
 * Performs given actions as a device user.
 *
 * @param interactor set of interactions
 * @throws IllegalStateException when actions are not performed in instrumentation thread
 */
@WorkerThread
fun user(interactor: User.() -> Unit) =
    interactor(UserInteractor)