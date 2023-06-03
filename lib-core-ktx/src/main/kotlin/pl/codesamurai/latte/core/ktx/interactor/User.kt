package pl.codesamurai.latte.core.ktx.interactor

import android.view.Surface
import androidx.annotation.WorkerThread
import pl.codesamurai.latte.core.matching.MatchingDsl

/**
 * Interface describing basic user interactions.
 */
public interface User {

    /**
     * Emulates user pressing power button.
     */
    public fun pressPower()

    /**
     * Emulates user pressing volume up button.
     */
    public fun pressVolumeUp()

    /**
     * Emulates user pressing volume down button.
     */
    public fun pressVolumeDown()

    /**
     * Emulates user pressing call button.
     */
    public fun pressCall()

    /**
     * Emulates user pressing end call button.
     */
    public fun pressEndCall()

    /**
     * Emulates user pressing camera button.
     */
    public fun pressCamera()

    /**
     * Emulates user pressing left soft key button.
     */
    public fun pressLeftSoftKey()

    /**
     * Emulates user pressing right soft key button.
     */
    public fun pressRightSoftKey()

    /**
     * Emulates user pressing menu button.
     */
    public fun pressMenu()

    /**
     * Emulates user pressing back button.
     */
    public fun pressBack()

    /**
     * Emulates user pressing home button.
     */
    public fun pressHome()

    /**
     * Emulates user pressing recent apps button.
     */
    public fun pressRecentApps()

    /**
     * Emulates user pressing given button.
     *
     * @param keyCode key code of a button
     */
    public fun pressKey(keyCode: Int)

    /**
     * Emulates user rotating a device.
     *
     * @param rotation one of [ROTATION_0], [ROTATION_90], [ROTATION_180], [ROTATION_270]
     */
    public fun rotateDevice(rotation: Int)

    public companion object {

        /**
         * Rotation angle 0 degrees (natural).
         */
        public const val ROTATION_0: Int = Surface.ROTATION_0

        /**
         * Rotation angle 90 degrees.
         */
        public const val ROTATION_90: Int = Surface.ROTATION_90

        /**
         * Rotation angle 180 degrees.
         */
        public const val ROTATION_180: Int = Surface.ROTATION_180

        /**
         * Rotation angle 270 degrees.
         */
        public const val ROTATION_270: Int = Surface.ROTATION_270
    }
}

/**
 * Performs given actions as a device user.
 *
 * @param interactor set of interactions
 * @throws IllegalStateException when actions are not performed in instrumentation thread
 */
@WorkerThread
@MatchingDsl
public fun user(interactor: User.() -> Unit): Unit =
    interactor(UserInteractor)