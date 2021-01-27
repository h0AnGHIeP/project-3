package com.edu.hoang

import android.content.Context
import androidx.startup.Initializer
import com.edu.hoang.net.NetworkService
import com.edu.hoang.store.LocalStorageService
import com.edu.hoang.store.PersonalRepository

class LocalStorageInitializer : Initializer<LocalStorageService> {
    override fun create(context: Context): LocalStorageService {
        NetworkService.initialize(context)
        LocalStorageService.initialize(context)
        return LocalStorageService
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}