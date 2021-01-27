package com.edu.hoang.net.dto

import java.sql.Time

data class DrugDto(
    val id: Long,
    val name: String,
    val instruct: String,
    val dailyUsing: Time,
    val unit: String,
    val note: String,
)