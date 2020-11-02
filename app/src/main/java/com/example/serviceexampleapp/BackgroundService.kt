package com.example.serviceexampleapp


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class BackgroundService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Utils.scheduleJobService(applicationContext) // reschedule the job
        createNotifications(this)
        Toast.makeText(this, "Background Service running", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun createNotifications(context: Context) {

        val btnOkClicked = Intent(context, ButtonClickReceiver::class.java)
        btnOkClicked.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingSwitchIntent = PendingIntent.getBroadcast(this, 1, btnOkClicked, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager = NotificationManagerCompat.from(context)
        val notifBuilder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifBuilder = NotificationCompat.Builder(context, createNotificationChannel(this, NotificationManager.IMPORTANCE_HIGH)
                    ?: "")
        } else {
            notifBuilder = NotificationCompat.Builder(context)
        }
        val notification = notifBuilder
                .setAutoCancel(false)
                .setContentTitle("hello")
                .setContentText("Testing notifications")
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_stat_name)
                .addAction(R.drawable.ic_stat_name, "Click here", pendingSwitchIntent)
        notificationManager.notify(0, notification.build())

    }

    private fun createNotificationChannel(context: Context, notificationImportance: Int = NotificationManager.IMPORTANCE_DEFAULT): String? {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

        return null
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        return true
    }
}