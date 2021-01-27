package com.edu.hoang.store.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.edu.hoang.store.data.Prescription
import com.edu.hoang.store.data.Instruction


data class PrescriptionWithInstructions(
    @Embedded
    val prescription: Prescription,
    @Relation(parentColumn = "id", entityColumn = "prescription_id")
    val instructions: List<Instruction>
)