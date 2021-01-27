package com.edu.hoang.ui.doctor

import androidx.lifecycle.*
import com.edu.hoang.net.dto.DoctorDto
import com.edu.hoang.store.*
import kotlinx.coroutines.*

class DoctorDetailsViewModel : ViewModel() {

    private val _doctor = MutableLiveData<DoctorDto>()
    val doctor: LiveData<DoctorDto>
        get() = _doctor

    val id: LiveData<String>
    val name: LiveData<String>
    val birth: LiveData<String>
    val country: LiveData<String>
    val card: LiveData<String>
    val address: LiveData<String>
    val contact: LiveData<String>
    val startWorking: LiveData<String>
    val faculty: LiveData<String>
    val hospital: LiveData<String>
    val position: LiveData<String>
    private val currentJob = Job()
    private val currentScope = CoroutineScope(Dispatchers.IO + currentJob)

    private val repository = PersonalRepository

    init {
        id = Transformations.map(doctor) { it.id.toString() }
        name = Transformations.map(doctor) { it.name }
        birth = Transformations.map(doctor) { ageFrom(it.birth).toString() }
        country = Transformations.map(doctor) { it.country }
        card = Transformations.map(doctor) { it.iCard }
        address = Transformations.map(doctor) { it.address }
        contact = Transformations.map(doctor) { it.contact }
        startWorking = Transformations.map(doctor) { formatDate(it.startWorking) }
        faculty = Transformations.map(doctor) { it.faculty }
        hospital = Transformations.map(doctor) { it.hospital }
        position = Transformations.map(doctor) { it.position }
        getDoctorDetails()
    }

    private fun getDoctorDetails() {
        currentScope.launch {
            val doctorDetails = repository.getDetails().doctor
            setUiValue(doctorDetails)
        }
    }


    private suspend fun setUiValue(dto: DoctorDto) {
        withContext(Dispatchers.Main) {
            _doctor.value = dto
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob.cancel()
    }
}