package edu.hmh.dto

import java.sql.Time

data class ClinicDto(
    val id: Long,
    val name: String,
    val address: String,
    val contact: String,
    val open: Time,
    val close: Time,
    val longitude: Double,
    val latitude: Double,
    val doctors: List<DoctorDto>
)