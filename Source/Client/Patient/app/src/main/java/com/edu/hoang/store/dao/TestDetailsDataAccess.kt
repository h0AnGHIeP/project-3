package com.edu.hoang.store.dao

import androidx.room.*
import com.edu.hoang.store.data.TestDetails

@Dao
interface TestDetailsDataAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(testDetails: TestDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(testDetails: List<TestDetails>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(testDetails: TestDetails)

    @Delete
    suspend fun delete(testDetails: TestDetails)

    @Query("SELECT * FROM TestDetails ORDER BY time DESC")
    suspend fun findAll(): List<TestDetails>

    @Query("SELECT * FROM TestDetails ORDER BY time DESC LIMIT :limit")
    suspend fun findLimit(limit: Int): List<TestDetails>

    @Query("DELETE FROM TestDetails")
    suspend fun deleteAll()

    @Transaction
    suspend fun renewWith(data: List<TestDetails>) {
        deleteAll()
        save(data)
    }
}