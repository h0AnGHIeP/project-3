package com.edu.hoang.ui.prescription

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.edu.hoang.AlarmSchedulerInitializer
import com.edu.hoang.notification.PillAlarmScheduler
import com.edu.hoang.store.*
import com.edu.hoang.store.PrescriptionRepository.PrescriptionDetails
import com.edu.hoang.store.data.Instruction
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class PrescriptionDetailsViewModel : ViewModel() {
    private val _prescriptionDetails = MutableLiveData<PrescriptionDetails>()

    val prescriptionDetails: LiveData<PrescriptionDetails>
        get() = _prescriptionDetails
    private val currentJob = Job()
    private val currentScope = CoroutineScope(Dispatchers.IO + currentJob)

    private val repository = PrescriptionRepository()
    private val settings = SettingsRepository
    private val remindRepository = ReminderRepository()


    fun addRemindTime(instruction: Instruction, hour: Int, minute: Int) {
        currentScope.launch {
            remindRepository.addRemind(hour, minute, "", instruction.id)
        }
    }


    fun setNotificationOn() {
        settings.notificationOn = true
    }

    fun setNotificationOff() {
        settings.notificationOn = false
    }

    fun renderLocalData() = currentScope.launch {
        val local = repository.localLatestPrescriptionOrDefault()
        setUiValues(local)
    }


    fun renderLatestData() = currentScope.launch {
        val recentData = repository.latestPrescription()
        setUiValues(recentData)
    }


    private suspend fun setUiValues(entries: PrescriptionDetails) {
        withContext(Dispatchers.Main) { _prescriptionDetails.value = entries }
    }


    override fun onCleared() {
        super.onCleared()
        currentJob.cancel()
    }
}
