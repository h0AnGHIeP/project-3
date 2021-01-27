package com.edu.hoang.store.dao

import androidx.room.*
import com.edu.hoang.store.data.Clinic


@Dao
interface ClinicDetailsDataAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(clinics: List<Clinic>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(clinics: List<Clinic>)

    @Delete
    suspend fun delete(clinics: List<Clinic>)

    @Query("SELECT * FROM Clinic")
    suspend fun findAll(): List<Clinic>

    @Query("SELECT * FROM Clinic ORDER BY ABS(longitude-:longitude) + ABS(latitude-:latitude) ASC LIMIT 1")
    suspend fun findNearest(longitude: Float, latitude: Float): Clinic

    @Query("SELECT * FROM Clinic WHERE id=:id")
    suspend fun findById(id: Long): Clinic

    @Query("DELETE FROM Clinic")
    suspend fun deleteAll()

    @Transaction
    suspend fun renewWith(clinics: List<Clinic>) {
        deleteAll()
        save(clinics)
    }
}