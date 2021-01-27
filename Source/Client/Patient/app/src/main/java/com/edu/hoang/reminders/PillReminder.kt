package com.edu.hoang.reminders

import android.content.*
import android.util.Log
import com.edu.hoang.store.LocalStorageService
import kotlinx.coroutines.*

class PillReminder : BroadcastReceiver() {
    companion object {
        const val PILL_REMINDER_ACTION = "com.edu.hoang.PILL_REMINDER_ACTION"
        const val INSTRUCTION_ID_KEY = "com.edu.hoang.INSTRUCTION_ID_KEY"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        if (intent.action == PILL_REMINDER_ACTION) {
            NotificationRegister.apply {
                initialize(context)
                val id = intent.extras!!.getLong(INSTRUCTION_ID_KEY)
                CoroutineScope(Dispatchers.Default).launch {
                    val drug = LocalStorageService.drugDao.value.findByInstruction(id)
                    val pillNotification = createNotification(
                        "Drug time: ${drug.name}", "It's time to take drugs"
                    )
                    withContext(Dispatchers.Main){ sendNotification(100, pillNotification) }
                }
            }
        }
    }
}