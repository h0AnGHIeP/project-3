package com.edu.hoang.net.dto

import java.util.*

data class PatientDto(
    val id: Long = 0,
    val name: String = "",
    val birth: Date = Date(),
    val job: String = "",
    val iCard: String = "",
    val address: String = "",
    val contact: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val sex: Boolean = false,
    val country: String = "",
    val begin: Date = Date(),
    val startTreatment: Date = Date(),
    val usualSymptom: String = "",
    val history: String = "",
    val type: Int = 0,
    val doctor: DoctorDto = DoctorDto()
)