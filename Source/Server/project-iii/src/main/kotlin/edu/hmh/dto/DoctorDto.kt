package edu.hmh.dto

import java.util.*

data class DoctorDto(
    val id: Long,
    val name: String,
    val birth: Date,
    val country: String,
    val iCard: String,
    val address: String,
    val contact: String,
    val startWorking: Date,
    val faculty: String,
    val hospital: String,
    val position: String
)