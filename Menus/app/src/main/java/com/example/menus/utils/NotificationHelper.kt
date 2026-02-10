package com.example.menus.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.menus.R

object NotificationHelper {

    private const val STATUS_CHANNEL_ID = "status_notifications"
    private const val BIRTHDAY_CHANNEL_ID = "birthday_notifications"
    private const val STATUS_NOTIFICATION_ID = 1
    private const val BIRTHDAY_BASE_ID = 100

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Status Notifications Channel
            val statusChannel = NotificationChannel(
                STATUS_CHANNEL_ID,
                "Team Status Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Hourly status notifications"
                enableVibration(true)
            }

            // Birthday Notifications Channel
            val birthdayChannel = NotificationChannel(
                BIRTHDAY_CHANNEL_ID,
                "Birthday Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Birthday reminder notifications"
                enableVibration(true)
                enableLights(true)
            }

            notificationManager.createNotificationChannel(statusChannel)
            notificationManager.createNotificationChannel(birthdayChannel)
        }
    }

    fun showStatusNotification(
        context: Context,
        title: String,
        message: String
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, STATUS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()

        notificationManager.notify(STATUS_NOTIFICATION_ID, notification)
    }

    fun showBirthdayNotification(
        context: Context,
        memberId: Int,
        memberName: String,
        message: String
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = BIRTHDAY_BASE_ID + memberId

        val notification = NotificationCompat.Builder(context, BIRTHDAY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("🎂 Birthday: $memberName")
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setVibrate(longArrayOf(0, 500, 250, 500))
            .build()

        notificationManager.notify(notificationId, notification)
    }

    fun cancelNotification(context: Context, notificationId: Int) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }

    fun cancelAllNotifications(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

    fun getTeamMemberStatus(): String {
        val statuses = listOf(
            "Soundar: Solving complex algorithms 💻",
            "Sethupathy: Coding with style ✨",
            "Suhas: Grinding Leetcode 🚀",
            "Team: Building amazing features 🎯",
            "Everyone: Making progress on tasks 📈"
        )
        return statuses.random()
    }
}
