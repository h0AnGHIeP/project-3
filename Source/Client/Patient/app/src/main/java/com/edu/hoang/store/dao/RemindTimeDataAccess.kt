package com.edu.hoang.store.dao

import androidx.room.*
import com.edu.hoang.store.data.RemindTime
import com.edu.hoang.store.relation.InstructionWithRemindTimes

@Dao
interface RemindTimeDataAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(time: RemindTime): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(time: List<RemindTime>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(time: RemindTime)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(time: List<RemindTime>)

    @Delete
    suspend fun delete(time: RemindTime)

    @Delete
    suspend fun delete(time: List<RemindTime>)

    @Query("SELECT * FROM RemindTime")
    suspend fun findAll(): List<RemindTime>

    @Query("SELECT * FROM RemindTime WHERE id=:id")
    suspend fun findById(id: Long): RemindTime

    @Transaction
    @Query("SELECT * FROM Instruction WHERE prescription_id=:prescriptionId")
    suspend fun findRecentRemindTime(prescriptionId: Long): List<InstructionWithRemindTimes>

    @Transaction
    @Query("SELECT * FROM Instruction WHERE id=:instructionId")
    suspend fun findRecentRemindTimeByInstruction(instructionId: Long): InstructionWithRemindTimes

}