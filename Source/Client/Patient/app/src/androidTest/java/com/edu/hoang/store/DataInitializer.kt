package com.edu.hoang.store

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.edu.hoang.store.dao.*
import com.edu.hoang.store.data.*
import kotlinx.coroutines.*
import java.sql.Time
import java.util.*

class DataInitializer(

) {
    val clinicDao: ClinicDetailsDataAccess
    val prescriptionDao: PrescriptionDataAccess
    val drugDao: DrugTypeDataAccess
    val testDao: TestDetailsDataAccess
    val remindDao: RemindTimeDataAccess

    init {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(
            appContext, MedicalDatabase::class.java
        ).build()

        clinicDao = database.clinicDao()
        prescriptionDao = database.prescriptionDao()
        drugDao = database.drugDao()
        testDao = database.testDao()
        remindDao = database.remindDao()
    }

    fun run(): Job = CoroutineScope(Dispatchers.IO).launch {
        val clinicLoading = async { insertClinics() }
        val drugLoading = async { insertDrugs() }
        val prescriptionLoading = async { insertPrescriptions() }
        val testLoading = async { insertTests() }
        val remindLoading = async { insertRemindTime() }
        clinicLoading.await()
        drugLoading.await()
        prescriptionLoading.await()
        testLoading.await()
        remindLoading.await()
    }


    private suspend fun insertClinics() {
        val data = listOf(
            Clinic(1, "Hong Ngoc", "Hai Ba Trung", longitude = 1.0f, latitude = 1.0f),
            Clinic(2, "Hung Viet", "Hai Ba Trung", longitude = 2.0f, latitude = 2.0f)
        )
        clinicDao.save(data)
    }

    private suspend fun insertPrescriptions() {
        val data = listOf(
            Prescription(1, Date(100)), Prescription(2, Date(200)),
        )

        val instructions = listOf(
            Instruction(1, prescriptionId = 1),
            Instruction(2, prescriptionId = 1),
            Instruction(3, prescriptionId = 2),
            Instruction(4, prescriptionId = 1)
        )
        prescriptionDao.save(data)
        prescriptionDao.saveInstructions(instructions)
    }

    private suspend fun insertDrugs() {
        val data = listOf(
            DrugType(1, "Insulin"),
            DrugType(2),
            DrugType(3),
        )
        drugDao.save(data)
    }

    private suspend fun insertTests() {
        val data = listOf(
            TestDetails(1, time = Date(100), index = 1),
            TestDetails(2, time = Date(200), index = 2),
            TestDetails(3, time = Date(300), index = 3),
        )
        testDao.save(data)
    }

    private suspend fun insertRemindTime() {
        val mockTime = Calendar.getInstance(Locale.US).apply { set(2021, 1, 4, 2, 0) }

        val data = listOf(
            RemindTime(time = Time(100), instructionId = 1),
            RemindTime(
                time = Time(mockTime.timeInMillis), active = true, instructionId = 2
            ),
            RemindTime(time = Time(300), active = true, instructionId = 3),
            RemindTime(time = Time(400), instructionId = 1),
        )
        remindDao.save(data)
    }

}