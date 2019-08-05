package com.test.latte.matcher

import android.app.Activity
import android.app.Instrumentation
import android.view.Window
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry.getInstance
import androidx.test.runner.lifecycle.Stage

@PublishedApi
internal class ActivityWindowProvider(
    private val instrumentation: Instrumentation = getInstrumentation(),
    private val monitor: ActivityLifecycleMonitor = getInstance()
) :
    WindowProvider {

    override fun getWindows(stage: Stage): List<Window> {
        lateinit var activities: Collection<Activity>

        instrumentation.runOnMainSync {
            activities = monitor.getActivitiesInStage(stage)
        }
        return activities.mapNotNull { it.window }
    }
}