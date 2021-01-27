package com.edu.hoang.store.data

import androidx.room.*
import java.sql.Time


@Entity
data class RemindTime(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var time: Time?,
    var note: String = "",
    var active: Boolean = false,
    @ColumnInfo(name = "instruction_id") val instructionId: Long = 0
)