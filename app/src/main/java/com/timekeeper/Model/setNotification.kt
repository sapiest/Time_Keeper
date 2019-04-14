package com.timekeeper.Model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.example.toxaxab.timekeeper.R
import com.timekeeper.Database.Entity.Activity
import com.timekeeper.Database.Entity.Status
import com.timekeeper.MainActivity
import java.util.*

class SetNotification(val context: Context, var mNotifyManager: NotificationManager?) {
    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"

    fun createNotificationChannel() {
        mNotifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            val notificationChannel = NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Timer Notification", NotificationManager
                    .IMPORTANCE_HIGH)
            notificationChannel.enableVibration(false)
            notificationChannel.enableLights(false)
            notificationChannel.description = "Notification from TimeKeeper"
            mNotifyManager!!.createNotificationChannel(notificationChannel)
        }
    }

    fun sendNotification(currentActivity: Activity?, currentStatus: Status?) {
        val notifyBuilder = getNotificationBuilder(currentActivity, currentStatus)
        mNotifyManager!!.notify(currentActivity!!.id, notifyBuilder.build())
    }


    private fun getNotificationIntent(currentActivity: Activity?): Intent {
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.putExtra("activity", currentActivity!!.id)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        return notificationIntent
    }

    private fun getNotificationBuilder(currentActivity: Activity?, currentStatus: Status?): NotificationCompat.Builder {
        val notificationIntent = getNotificationIntent(currentActivity)

        val notificationPendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationStopIntent = getNotificationIntent(currentActivity)
        notificationStopIntent.action = "STOP"
        val notificationPendingStop = PendingIntent.getActivity(context,
                0, notificationStopIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val remoteViews = RemoteViews(context.packageName, R.layout.notification)
        remoteViews.setTextViewText(R.id.textView, currentActivity!!.name)
        remoteViews.setChronometer(R.id.timer, SystemClock.elapsedRealtime() - currentStatus!!.current_time, "%s", true)
        remoteViews.setOnClickPendingIntent(R.id.root, notificationPendingIntent)
        remoteViews.setOnClickPendingIntent(R.id.n_btn_stop, notificationPendingStop)
        return NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_play)
                .setCustomContentView(remoteViews)
                .setContentIntent(notificationPendingIntent)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setShowWhen(false)
                .setOngoing(true)
    }

    fun cancelNotification(activity: Activity?) {
        mNotifyManager?.cancel(activity!!.id)
    }
}