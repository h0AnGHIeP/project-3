package com.edu.hoang.store.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.edu.hoang.store.data.DrugType
import com.edu.hoang.store.data.Instruction

class DrugWithInstructions(
    @Embedded
    val drug: DrugType,
    @Relation(parentColumn = "id", entityColumn = "drug_id")
    val instructions: List<Instruction>
)