package com.edu.hoang

import android.content.Context
import androidx.startup.Initializer
import com.edu.hoang.notification.PillAlarmScheduler
import com.edu.hoang.store.LocalStorageService

class AlarmSchedulerInitializer : Initializer<PillAlarmScheduler> {
    override fun create(context: Context): PillAlarmScheduler {
        PillAlarmScheduler.initialize(context)
        return PillAlarmScheduler
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(LocalStorageInitializer::class.java)
    }
}