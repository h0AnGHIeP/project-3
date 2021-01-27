package com.edu.hoang.ui.dashboard

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import com.edu.hoang.net.dto.PatientDto
import com.edu.hoang.store.*
import kotlinx.coroutines.*

class DashboardViewModel(private val personalId: Long) : ViewModel() {
    private val repository = PersonalRepository.apply { id = personalId }
    private val currentJob = Job()
    private val currentScope = CoroutineScope(Dispatchers.IO + currentJob)

    private val _patientDetails = MutableLiveData<PatientDto>()
    private val patientDetails: LiveData<PatientDto>
        get() = _patientDetails

    val id: LiveData<String>
    val name: LiveData<String>
    val age: LiveData<String>
    val job: LiveData<String>
    val card: LiveData<String>
    val address: LiveData<String>
    val contact: LiveData<String>
    val height: LiveData<String>
    val weight: LiveData<String>
    val sex: LiveData<String>
    val country: LiveData<String>
    val begin: LiveData<String>
    val startTreatment: LiveData<String>
    val usualSymptom: LiveData<String>
    val history: LiveData<String>
    val type: LiveData<String>


    init {
        id = map(patientDetails) { it.id.toString() }
        name = map(patientDetails) { it.name }
        age = map(patientDetails) { ageFrom(it.birth).toString() }
        job = map(patientDetails) { it.job }
        card = map(patientDetails) { it.iCard }
        address = map(patientDetails) { it.address }
        contact = map(patientDetails) { it.contact }
        height = map(patientDetails) { it.height.toString() }
        weight = map(patientDetails) { it.weight.toString() }
        sex = map(patientDetails) { if (it.sex) "Male" else "Female" }
        country = map(patientDetails) { it.country }
        begin = map(patientDetails) { formatDate(it.begin) }
        startTreatment = map(patientDetails) { formatDate(it.startTreatment) }
        usualSymptom = map(patientDetails) { it.usualSymptom }
        history = map(patientDetails) { it.history }
        type = map(patientDetails) { it.type.toString() }
        updatePatientDetails()
    }

    private fun updatePatientDetails() {
        currentScope.launch {
            val currentDetails = repository.getDetails()
            setUiValue(currentDetails)
        }
    }

    private suspend fun setUiValue(patientDetails: PatientDto) {
        withContext(Dispatchers.Main) {
            _patientDetails.value = patientDetails
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob.cancel()
    }
}