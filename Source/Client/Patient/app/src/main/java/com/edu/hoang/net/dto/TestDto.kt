package com.edu.hoang.net.dto

import java.util.*

data class TestDto(
    val id: Long,
    val time: Date,
    val description: String,
    val HbA1CIndex: Float,
    val randomIndex: Float,
    val cravingIndex: Float,
    val afterMealIndex: Float,
    val patientId: Long
)