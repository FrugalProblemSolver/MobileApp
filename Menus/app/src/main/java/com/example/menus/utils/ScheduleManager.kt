package com.example.menus.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.menus.workers.StatusNotificationWorker
import java.util.concurrent.TimeUnit

object ScheduleManager {

    private const val HOURLY_STATUS_WORK_NAME = "hourly_status_notification"

    fun scheduleHourlyStatusNotifications(context: Context) {
        val statusWorkRequest = PeriodicWorkRequestBuilder<StatusNotificationWorker>(
            1, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            HOURLY_STATUS_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            statusWorkRequest
        )
    }

    fun cancelHourlyStatusNotifications(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(HOURLY_STATUS_WORK_NAME)
    }

    fun cancelAllWork(context: Context) {
        WorkManager.getInstance(context).cancelAllWork()
    }

    /**
     * Get the current schedule status (Example: "9 AM - 5 PM, Monday to Friday")
     */
    fun getScheduleInfo(): String {
        return "📅 Team Status: Every Hour (9 AM - 5 PM, Mon-Fri)"
    }
}
