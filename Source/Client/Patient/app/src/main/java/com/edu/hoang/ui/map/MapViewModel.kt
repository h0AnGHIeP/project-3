package com.edu.hoang.ui.map

import androidx.lifecycle.*
import com.edu.hoang.store.ClinicRepository
import com.edu.hoang.store.data.Clinic
import kotlinx.coroutines.*

class MapViewModel : ViewModel() {
    private val repository = ClinicRepository()
    private val _clinics = MutableLiveData<List<Clinic>>()
    val clinics: LiveData<List<Clinic>>
        get() = _clinics


    private val currentJob = Job()
    private val currentScope = CoroutineScope(Dispatchers.IO + currentJob)



    fun renderLocal() {
        currentScope.launch {
            val localClinics = repository.localClinic()
            setUiValues(localClinics)
        }
    }


    fun renderLatest() {
        currentScope.launch {
            val currentClinics = repository.reload()
            setUiValues(currentClinics)
        }
    }


    private suspend fun setUiValues(data: List<Clinic>) {
        withContext(Dispatchers.Main) {
            _clinics.value = data
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob.cancel()
    }
}