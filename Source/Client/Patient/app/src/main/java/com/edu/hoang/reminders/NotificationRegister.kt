package com.edu.hoang.reminders

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.edu.hoang.MainActivity
import com.edu.hoang.R

object NotificationRegister {
    private lateinit var appContext: Context

    private const val CHANNEL_ID = "com.edu.hoang.PILL_REMINDER_CHANNEL"

    fun initialize(context: Context) {
        appContext = context
        registerChannel()
    }

    private fun getNotificationManager() = appContext.getSystemService(NotificationManager::class.java)

    private fun registerChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            appContext.getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getNotificationManager().createNotificationChannel(channel)
    }

    fun createNotification(title: String, content: String): Notification {
        val intent = Intent(appContext, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val pending = PendingIntent.getActivity(
            appContext,
            MainActivity.REQUEST_REMINDER,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(appContext, CHANNEL_ID).apply {
            color = Color.LTGRAY
            setSmallIcon(R.drawable.ic_prescription)
            setContentTitle(title)
            setContentText(content)
            setContentIntent(pending)
        }.build()
    }

    fun sendNotification(id: Int, note: Notification) {
        getNotificationManager().notify(id, note)
    }
}