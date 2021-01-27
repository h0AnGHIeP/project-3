package com.edu.hoang.store.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.edu.hoang.store.data.Instruction
import com.edu.hoang.store.data.RemindTime

data class InstructionWithRemindTimes(
    @Embedded
    val instruction: Instruction,
    @Relation(parentColumn = "id",entityColumn = "instruction_id")
    val remindTimes: List<RemindTime>
)