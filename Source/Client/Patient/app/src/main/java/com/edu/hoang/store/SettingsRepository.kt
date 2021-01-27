package com.edu.hoang.store

import android.content.Context
import android.content.SharedPreferences
import com.edu.hoang.reminders.NotificationRegister

object SettingsRepository {
    private const val SETTINGS_FILE = "com.edu.hoang.store.settings_preferences"
    private const val NOTIFICATION_KEY = "com.edu.hoang.store.shared.settings"

    private lateinit var settingsSharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        settingsSharedPreferences = context.getSharedPreferences(
            SETTINGS_FILE, Context.MODE_PRIVATE
        )
        NotificationRegister.initialize(context)
    }

    var notificationOn: Boolean
        get() = settingsSharedPreferences.getBoolean(NOTIFICATION_KEY, true)
        set(value) = settingsSharedPreferences.edit().putBoolean(NOTIFICATION_KEY, value).apply()



}