package com.edu.hoang.store

import android.content.Context
import android.content.SharedPreferences
import com.edu.hoang.net.NetworkService
import com.edu.hoang.net.PersonalDetailsApi
import com.edu.hoang.net.dto.PatientDto
import com.google.gson.GsonBuilder
import kotlinx.coroutines.withTimeoutOrNull

object PersonalRepository {

    private const val PERSONAL_PREFERENCES_FILE = "com.edu.hoang.store.personal_details_preferences"
    private const val PERSONAL_KEY = "com.edu.hoang.store.shared.personal_key"
    private lateinit var personalDetailsPreferences: SharedPreferences
    private val gson = GsonBuilder().create()


    private lateinit var personalDetailsApi: PersonalDetailsApi
    private var personalDetails: PatientDto? = null
    fun initialize(context: Context) {
        personalDetailsPreferences = context.getSharedPreferences(
            PERSONAL_PREFERENCES_FILE, Context.MODE_PRIVATE
        )
        personalDetailsApi = NetworkService.personalDetailsApi.value
    }

    private var isIdSet = false
    var id = 0L
        set(value) {
            if (!isIdSet) {
                field = value
                isIdSet = true
            }
        }

    fun localId() = getDetailsFromPreferences()?.id


    private suspend fun fetchFromApi(patientId: Long): PatientDto? {
        return withTimeoutOrNull(NetworkService.NETWORK_TIMEOUT) {
            try {
                personalDetailsApi.fetchPatientDetails(patientId)
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun getDetailsFromPreferences(): PatientDto? {
        val detailsJson = personalDetailsPreferences.getString(PERSONAL_KEY, null)
        return if (detailsJson.isNullOrEmpty()) null
        else gson.fromJson(detailsJson, PatientDto::class.java)
    }

    private fun saveToPreferences(patientDetails: PatientDto) {
        val detailsJson = gson.toJson(patientDetails)
        personalDetailsPreferences.edit().putString(PERSONAL_KEY, detailsJson).apply()
    }

    // Core logic
    suspend fun getDetails(): PatientDto {
        if (personalDetails != null) return personalDetails!!
        val preferencesDetails = getDetailsFromPreferences()
        return if (preferencesDetails != null && preferencesDetails.id == id) {
            personalDetails = preferencesDetails
            personalDetails!!
        } else {
            var apiDetails = fetchFromApi(id)
            clearDatabase()
            if (apiDetails == null) apiDetails = PatientDto(name = "Invalid")
            else {
                saveToPreferences(apiDetails)
                personalDetails = apiDetails
            }
            apiDetails
        }
    }

    private suspend fun clearDatabase() {
        LocalStorageService.clearAllPrescriptionsData()
    }

}

