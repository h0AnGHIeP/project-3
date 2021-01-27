package com.edu.hoang.store.dao

import androidx.room.*
import com.edu.hoang.store.data.DrugType

@Dao
interface DrugTypeDataAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(drugTypes: List<DrugType>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(drugType: DrugType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(drugTypes: List<DrugType>)

    @Delete
    suspend fun delete(drugTypes: List<DrugType>)

    @Query("SELECT * FROM DrugType")
    suspend fun findAll(): List<DrugType>

    @Query("DELETE FROM DrugType")
    suspend fun deleteAllDrugTypes()

    @Query("SELECT DISTINCT DrugType.* FROM DrugType INNER JOIN Instruction ON DrugType.id=Instruction.drug_id WHERE Instruction.id=:instructionId ")
    suspend fun findByInstruction(instructionId: Long): DrugType
}
