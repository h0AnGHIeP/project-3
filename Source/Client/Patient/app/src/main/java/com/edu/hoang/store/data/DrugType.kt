package com.edu.hoang.store.data

import androidx.room.*
import com.edu.hoang.net.dto.DrugDto
import java.sql.Time

@Entity
data class DrugType(
    @PrimaryKey val id: Long,
    val name: String = "",
    val instruct: String = "",
    @ColumnInfo(name = "daily_using") val dailyUsing: Time = Time(0),
    val unit: String = "",
    val note: String = "",
) {
    constructor(dto: DrugDto) : this(
        dto.id, dto.name, dto.instruct, dto.dailyUsing, dto.unit, dto.note
    )
}
