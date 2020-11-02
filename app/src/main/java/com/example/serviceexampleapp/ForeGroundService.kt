package com.example.serviceexampleapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class ForeGroundService : Service() {


    override fun onCreate() {
        super.onCreate()
        showNotification()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun showNotification() {
        val btnOkClicked = Intent(this, MainActivity::class.java)
        btnOkClicked.putExtra("isButtonClicked", true)
        btnOkClicked.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingSwitchIntent =
            PendingIntent.getActivity(this, 1, btnOkClicked, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager = NotificationManagerCompat.from(this)
        val notifBuilder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifBuilder = NotificationCompat.Builder(
                this, createNotificationChannel(this, NotificationManager.IMPORTANCE_HIGH)
                    ?: ""
            )
        } else {
            notifBuilder = NotificationCompat.Builder(this)
        }
        val notification = notifBuilder
            .setAutoCancel(false)
            .setContentTitle("hello")
            .setContentText("Testing notifications")
            .setPriority(Notification.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_stat_name)
            .addAction(R.drawable.ic_stat_name, "Click here", pendingSwitchIntent)
        notificationManager.notify(0, notification.build())

        startForeground(1, notification.notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        context: Context,
        notificationImportance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ): String? {
        val descriptionText = "description"
        val channelId = context.getString(R.string.default_notification_channel_id)
        val channel = NotificationChannel(channelId, channelId, notificationImportance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return channelId

    }

}