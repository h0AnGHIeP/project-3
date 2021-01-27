package com.edu.hoang.store.dao

import androidx.room.*
import com.edu.hoang.store.data.Instruction
import com.edu.hoang.store.data.Prescription
import com.edu.hoang.store.relation.PrescriptionWithInstructions

@Dao
interface PrescriptionDataAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(prescription: Prescription)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(prescriptions: List<Prescription>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(prescription: Prescription)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(prescription: List<Prescription>)

    @Delete
    suspend fun delete(prescription: Prescription)

    @Transaction
    @Query("SELECT * FROM Prescription ORDER BY starting_time DESC LIMIT 1")
    suspend fun findLatest(): PrescriptionWithInstructions?

    @Transaction
    @Query("SELECT * FROM Prescription WHERE id = :id")
    suspend fun findById(id: Long): PrescriptionWithInstructions?

    @Query("SELECT * FROM Prescription")
    suspend fun findAll(): List<Prescription>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInstructions(instructions: List<Instruction>)

    @Delete
    suspend fun deleteInstructions(instructions: List<Instruction>)

    @Query("DELETE FROM Prescription")
    suspend fun deleteAllPrescriptions()

    @Query("DELETE FROM Instruction")
    suspend fun deleteAllInstructions()
}