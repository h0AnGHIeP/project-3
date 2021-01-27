package com.edu.hoang.net.dto

import java.sql.Time

data class ClinicDto(
    val id: Long,
    val name: String,
    val address: String,
    val contact: String,
    val open: Time,
    val close: Time,
    val longitude: Float,
    val latitude: Float,
    val doctors: List<DoctorDto>
)