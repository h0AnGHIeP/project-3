package com.edu.hoang.ui.prescription

import androidx.lifecycle.*
import com.edu.hoang.store.PrescriptionRepository
import com.edu.hoang.store.data.Prescription
import kotlinx.coroutines.*

class PrescriptionHistoryViewModel : ViewModel() {
    private val repository = PrescriptionRepository()
    private val currentJob = Job()
    private val currentScope = CoroutineScope(Dispatchers.IO + currentJob)
    val history: LiveData<List<Prescription>>
        get() = _history

    private val _history = MutableLiveData<List<Prescription>>()

    fun renderLocalHistory() = currentScope.launch {
        val localHistory = repository.allLocalPrescription()
        setUiValues(localHistory)
    }


    fun renderCurrentHistory() = currentScope.launch {
        val current = repository.reloadHistory()
        setUiValues(current)
    }


    private suspend fun setUiValues(history: List<Prescription>) {
        withContext(Dispatchers.Main) { _history.value = history }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob.cancel()
    }

}