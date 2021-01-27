package com.edu.hoang.net.dto

data class InstructionDto(
    val id: Long,
    val duration: Int,
    val dose: Float,
    val content: String,
    val drugId: Long,
)