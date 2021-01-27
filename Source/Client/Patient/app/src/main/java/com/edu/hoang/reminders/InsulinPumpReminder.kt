package com.edu.hoang.reminders

import android.content.*
import android.util.Log

class InsulinPumpReminder : BroadcastReceiver() {
    companion object {
        const val PUMP_REMINDER_ACTION = "com.edu.hoang.PUMP_REMINDER_ACTION"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        if (intent?.action == PUMP_REMINDER_ACTION) {
            NotificationRegister.apply {
                initialize(context)
                val pillNotification = createNotification("INSULIN", "Pump insulin")
                sendNotification(100, pillNotification)
            }
        }

    }
}