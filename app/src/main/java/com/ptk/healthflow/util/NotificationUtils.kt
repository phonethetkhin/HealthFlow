package com.ptk.healthflow.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ptk.healthflow.R

object NotificationUtils {

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "health_alerts", // Channel ID
                "Health Alerts", // Channel name
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for health alerts (fever, high heart rate)"
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showHealthNotification(context: Context, title: String, message: String) {
        Log.e("testASDF", "SEquence ShowNOti5")

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "health_alerts")
            .setSmallIcon(R.drawable.health_flow_logo) // Set your own small icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // Auto dismiss the notification when clicked
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Use sound and vibration
            .build()

        notificationManager.notify(1, notification)
        Log.e("testASDF", "SEquence ShowNOti6")

    }
}
