package com.edu.hoang.store.data

import androidx.room.*
import com.edu.hoang.net.dto.PrescriptionDto
import java.util.*

@Entity
data class Prescription(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "starting_time") val startTime: Date = Date(),
    @ColumnInfo(name = "end_time") val endTime: Date = Date(),
    val expect: String = "",
    val note: String = "",
) {
    constructor(dto: PrescriptionDto) : this(
        dto.id, dto.startTime, dto.endTime, dto.expect, dto.note
    )
}