package com.example.menus.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.menus.utils.NotificationHelper

class BirthdayNotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            val memberName = inputData.getString("memberName") ?: return Result.failure()
            val memberId = inputData.getInt("memberId", -1)

            if (memberId == -1) return Result.failure()

            val greetingMessage = "🎉 Happy Birthday! Wishing $memberName an amazing day!"

            NotificationHelper.showBirthdayNotification(
                applicationContext,
                memberId,
                memberName,
                greetingMessage
            )

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
