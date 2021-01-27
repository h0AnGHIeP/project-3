package com.edu.hoang.net.dto

import java.util.*

data class PrescriptionDto(
    val id: Long,
    val startTime: Date,
    val endTime: Date,
    val expect: String,
    val note: String,
    val patientId: Long,
    val instructions: List<InstructionDto>
)