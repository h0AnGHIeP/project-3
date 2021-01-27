package edu.hmh.dto

import java.util.*

data class PatientDto(
    val id: Long,
    val name: String,
    val birth: Date,
    val job: String,
    val iCard: String,
    val address: String,
    val contact: String,
    val height: Int,
    val weight: Int,
    val sex: Boolean,
    val country: String,
    val begin: Date,
    val startTreatment: Date,
    val usualSymptom: String,
    val history: String,
    val type: Int,
    val doctor: DoctorDto
)