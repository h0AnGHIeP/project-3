package com.edu.hoang.store.data

import androidx.room.*
import com.edu.hoang.net.dto.InstructionDto

@Entity
data class Instruction(
    @PrimaryKey val id: Long,
    val duration: Int = 0,
    val dose: Float = 0F,
    val content: String = "",
    @ColumnInfo(name = "drug_id") val drugId: Long = 0L,
    @ColumnInfo(name = "prescription_id") val prescriptionId: Long = 0L
) {
    constructor(dto: InstructionDto, prescriptionId: Long) : this(
        dto.id, dto.duration, dto.dose, dto.content, dto.drugId, prescriptionId
    )
}
