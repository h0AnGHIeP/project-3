package com.edu.hoang.net.dto

import java.util.*

data class DoctorDto(
    val id: Long = 0,
    val name: String = "",
    val birth: Date = Date(),
    val country: String = "",
    val iCard: String = "",
    val address: String = "",
    val contact: String = "",
    val startWorking: Date = Date(),
    val faculty: String = "",
    val hospital: String = "",
    val position: String = ""
)