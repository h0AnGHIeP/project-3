package com.edu.hoang.store

import androidx.room.*
import com.edu.hoang.store.dao.*
import com.edu.hoang.store.data.*

@Database(
    entities = [DrugType::class, Prescription::class, TestDetails::class, Instruction::class, Clinic::class, RemindTime::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class MedicalDatabase : RoomDatabase() {
    abstract fun clinicDao(): ClinicDetailsDataAccess
    abstract fun prescriptionDao(): PrescriptionDataAccess
    abstract fun drugDao(): DrugTypeDataAccess
    abstract fun testDao(): TestDetailsDataAccess
    abstract fun remindDao(): RemindTimeDataAccess
}