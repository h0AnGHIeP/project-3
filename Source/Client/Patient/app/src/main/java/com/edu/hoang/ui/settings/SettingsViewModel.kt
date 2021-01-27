package com.edu.hoang.ui.settings

import androidx.lifecycle.*
import com.edu.hoang.store.*
import com.edu.hoang.store.data.*
import kotlinx.coroutines.*

class SettingsViewModel : ViewModel() {

    private val settings = SettingsRepository
    private val repository = ReminderRepository()
    private val prescriptionRepository = PrescriptionRepository()
    private val currentJob = Job()
    private val currentScope = CoroutineScope(Dispatchers.IO + currentJob)

    private lateinit var recentPrescription: PrescriptionRepository.PrescriptionDetails

    private val _remindTime = MutableLiveData<List<Pair<RemindTime, DrugType>>>()
    val remindTime: LiveData<List<Pair<RemindTime, DrugType>>>
        get() = _remindTime

    fun setNotificationOn() {
        settings.notificationOn = true
    }

    fun setNotificationOff() {
        settings.notificationOn = false
    }

    fun renderRemindTime() {
        currentScope.launch {
            recentPrescription = prescriptionRepository.localLatestPrescriptionOrDefault()
            val localData = repository.activeRemindTimes(recentPrescription.prescription.id)
            val packedRemindTimeData = pack(localData, recentPrescription.instructions)
            setUiValues(packedRemindTimeData)
        }
    }

    private fun pack(
        remindTimes: List<RemindTime>,
        pairedInstructions: List<Pair<Instruction, DrugType>>
    ): List<Pair<RemindTime, DrugType>>? {
        if (remindTimes.isEmpty() || pairedInstructions.isEmpty()) return null
        val result = mutableListOf<Pair<RemindTime, DrugType>>()
        remindTimes.forEach { remind ->
            val drugType = pairedInstructions.find { pairedInstruction ->
                pairedInstruction.first.id == remind.instructionId
            }!!.second
            val pair = remind to drugType
            result.add(pair)
        }
        return result
    }

    fun updateTime(remindTime: RemindTime, hour: Int, minute: Int) {
        currentScope.launch {
            repository.changeReminder(
                remindTime, hour, minute, null
            )
            setUiValues()
        }
    }

    fun deleteTime(remindTime: RemindTime) {
        currentScope.launch {
            repository.deleteRemind(remindTime)
            val filteredValues = _remindTime.value?.filterNot {
                it.first == remindTime
            }
            setUiValues(filteredValues)
        }
    }

    fun disableRemind(remindTime: RemindTime) {
        currentScope.launch {
            repository.deactivateRemind(remindTime)
        }
    }

    fun enableRemind(remindTime: RemindTime) {
        currentScope.launch {
            repository.activateRemind(remindTime)
        }
    }

    private suspend fun setUiValues(data: List<Pair<RemindTime, DrugType>>? = null) {
        withContext(Dispatchers.Main) {
            if (data != null) {
                _remindTime.value = data
            } else _remindTime.value = _remindTime.value
        }

    }

    override fun onCleared() {
        super.onCleared()
        currentJob.cancel()
    }
}