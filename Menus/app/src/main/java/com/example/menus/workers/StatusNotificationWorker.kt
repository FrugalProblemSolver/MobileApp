package com.example.menus.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.menus.utils.NotificationHelper
import com.example.menus.utils.ScheduleManager
import java.util.Calendar

class StatusNotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            // Check if current time is within working hours
            if (isWithinWorkingHours()) {
                val status = NotificationHelper.getTeamMemberStatus()
                NotificationHelper.showStatusNotification(
                    applicationContext,
                    "HumbleHackers Team Status",
                    status
                )
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private fun isWithinWorkingHours(): Boolean {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        // Check if current time is between 9 AM and 5 PM
        val isWorkingHour = currentHour in 9..16 // 9 AM to 4:59 PM
        
        // Check if it's a weekday (Monday to Friday)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val isWeekday = dayOfWeek !in arrayOf(Calendar.SATURDAY, Calendar.SUNDAY)

        return isWorkingHour && isWeekday
    }
}
