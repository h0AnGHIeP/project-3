package com.edu.hoang.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.edu.hoang.reminders.PillReminder
import com.edu.hoang.store.*
import com.edu.hoang.store.data.RemindTime
import kotlinx.coroutines.*
import java.util.*

object PillAlarmScheduler {
    private lateinit var appContext: Context
    private fun getAlarmManager() = appContext.getSystemService(AlarmManager::class.java)


    private val reminderDao = ReminderRepository()

    fun initialize(context: Context) {
        appContext = context
        CoroutineScope(Dispatchers.IO).launch {
            fireAlarmScheduler()
        }
    }

    private fun intentOf(instructionId: Long) = Intent(
        appContext, PillReminder::class.java
    ).apply {
        action = PillReminder.PILL_REMINDER_ACTION
        putExtra(PillReminder.INSTRUCTION_ID_KEY, instructionId)
    }

    fun setReminder(
        intervalMillis: Long,
        reminderId: Long,
        instructionId: Long,
        hour: Int,
        minute: Int = 0,
        second: Int = 0
    ) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
        }
        setReminder(intervalMillis, reminderId, instructionId, calendar)
    }

    private fun setReminder(
        intervalMillis: Long, reminderId: Long, instructionId: Long, calendar: Calendar
    ) {
        val startPendingIntent = PendingIntent.getBroadcast(
            appContext,
            reminderId.toInt(),
            intentOf(instructionId),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        getAlarmManager().setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            intervalMillis,
            startPendingIntent
        )
    }

    suspend fun fireAlarmScheduler() {
        reminderDao.currentRemindTimes().forEach {
            if (it.active) {
                val hour = it.time?.getHour() ?: 12
                val minute = it.time?.getMinute() ?: 0
                setReminder(
                    AlarmManager.INTERVAL_DAY, it.id ?: 0, it.instructionId, hour, minute
                )
            }
        }
    }

    fun cancelAlarm(remind: RemindTime) {
        val cancelPendingIntent = PendingIntent.getService(
            appContext,
            remind.id!!.toInt(),
            intentOf(remind.instructionId),
            PendingIntent.FLAG_NO_CREATE
        )
        if (cancelPendingIntent != null) getAlarmManager().cancel(cancelPendingIntent)
    }
}