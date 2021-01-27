package com.edu.hoang.store

import android.app.AlarmManager
import com.edu.hoang.notification.PillAlarmScheduler
import com.edu.hoang.store.dao.PrescriptionDataAccess
import com.edu.hoang.store.dao.RemindTimeDataAccess
import com.edu.hoang.store.data.RemindTime
import java.sql.Time
import java.util.*

class ReminderRepository(
    private val dao: RemindTimeDataAccess = LocalStorageService.remindDao.value,
    private val prescriptionDao: PrescriptionDataAccess = LocalStorageService.prescriptionDao.value
) {

    suspend fun addRemind(hour: Int, minute: Int, note: String, instructionId: Long) {
        val millis = Calendar.getInstance(Locale.US).apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }.timeInMillis
        val remindTime = RemindTime(
            time = Time(millis), note = note, active = true, instructionId = instructionId
        )
        val remindId = dao.save(remindTime)
        PillAlarmScheduler.setReminder(
            AlarmManager.INTERVAL_DAY, remindId, instructionId, hour, minute
        )
    }

    suspend fun deleteRemind(remindTime: RemindTime) {
        dao.delete(remindTime)
        PillAlarmScheduler.cancelAlarm(remindTime)
    }

    suspend fun deactivateRemind(remindTime: RemindTime) {
        remindTime.active = false
        dao.update(remindTime)
        PillAlarmScheduler.cancelAlarm(remindTime)
    }

    suspend fun activateRemind(remindTime: RemindTime) {
        remindTime.active = true
        dao.update(remindTime)
        PillAlarmScheduler.fireAlarmScheduler()
    }

    suspend fun changeReminder(
        remindTime: RemindTime, hour: Int?, minute: Int?, newNote: String?
    ) {
        var recentTime = remindTime.time ?: return
        recentTime = getTimeFrom(
            hour ?: recentTime.getHour(), minute ?: recentTime.getMinute()
        )
        remindTime.apply {
            note = newNote ?: note
            time = recentTime
        }
        dao.update(remindTime)
        PillAlarmScheduler.fireAlarmScheduler()
    }

    suspend fun activeRemindTimes(prescriptionId: Long): List<RemindTime> {
        val timeInstances = dao.findRecentRemindTime(prescriptionId).flatMap {
            it.remindTimes
        }
        return timeInstances.filter { it.active }
    }

    suspend fun currentRemindTimes(): List<RemindTime> {
        val prescriptionId = prescriptionDao.findLatest()?.prescription?.id
            ?: return emptyList()
        return activeRemindTimes(prescriptionId)
    }


}