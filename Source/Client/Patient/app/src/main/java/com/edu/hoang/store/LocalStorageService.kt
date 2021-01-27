package com.edu.hoang.store

import android.content.Context
import androidx.room.Room
import com.edu.hoang.store.dao.*


object LocalStorageService {

    private lateinit var appContext: Context
    private lateinit var database: Lazy<MedicalDatabase>
    private const val DATABASE_NAME = "com.edu.hoang.medical.database"

    internal lateinit var clinicDao: Lazy<ClinicDetailsDataAccess>
    internal lateinit var prescriptionDao: Lazy<PrescriptionDataAccess>
    internal lateinit var drugDao: Lazy<DrugTypeDataAccess>
    internal lateinit var testDao: Lazy<TestDetailsDataAccess>
    internal lateinit var remindDao: Lazy<RemindTimeDataAccess>


    fun initialize(context: Context) {
        PersonalRepository.initialize(context)
        SettingsRepository.initialize(context)
        appContext = context
        database = lazy {
            Room.databaseBuilder(appContext, MedicalDatabase::class.java, DATABASE_NAME)
                .build()
        }
        clinicDao = lazy { database.value.clinicDao() }
        prescriptionDao = lazy { database.value.prescriptionDao() }
        drugDao = lazy { database.value.drugDao() }
        testDao = lazy { database.value.testDao() }
        remindDao = lazy { database.value.remindDao() }
    }

    internal suspend fun clearAllPrescriptionsData() {
        prescriptionDao.value.deleteAllInstructions()
        prescriptionDao.value.deleteAllPrescriptions()
        testDao.value.deleteAll()
    }

}