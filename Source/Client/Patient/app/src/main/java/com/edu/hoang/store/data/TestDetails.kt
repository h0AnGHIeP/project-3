package com.edu.hoang.store.data

import androidx.room.*
import com.edu.hoang.net.dto.TestDto
import java.util.*

@Entity
data class TestDetails(
    val id: Long = -1,
    val time: Date = Date(),
    val description: String = "",
    @ColumnInfo(name = "hba1c_index") val hba1cIndex: Float = 0F,
    @ColumnInfo(name = "random_index") val randomIndex: Float = 0F,
    @ColumnInfo(name = "craving_index") val cravingIndex: Float = 0F,
    @ColumnInfo(name = "after_meal_index") val afterMealIndex: Float = 0F,
    @PrimaryKey(autoGenerate = true) val index: Long? = null
) {
    constructor(dto: TestDto) : this(
        dto.id,
        dto.time,
        dto.description,
        dto.HbA1CIndex,
        dto.randomIndex,
        dto.cravingIndex,
        dto.afterMealIndex
    )

    fun dto(patientId: Long) = TestDto(
        id,
        time,
        description,
        hba1cIndex,
        randomIndex,
        cravingIndex,
        afterMealIndex,
        patientId
    )
}