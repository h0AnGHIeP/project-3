package com.edu.hoang.store

import com.edu.hoang.net.ClinicDetailsApi
import com.edu.hoang.net.NetworkService.NETWORK_TIMEOUT
import com.edu.hoang.net.NetworkService.clinicDetailsApi
import com.edu.hoang.store.LocalStorageService.clinicDao
import com.edu.hoang.store.dao.ClinicDetailsDataAccess
import com.edu.hoang.store.data.Clinic
import kotlinx.coroutines.*

class ClinicRepository(
    private val api: ClinicDetailsApi = clinicDetailsApi.value,
    private val dao: ClinicDetailsDataAccess = clinicDao.value
) {
    suspend fun localClinic() = dao.findAll()
    suspend fun reload() = coroutineScope {
        val clinics = withTimeoutOrNull(NETWORK_TIMEOUT) {
            try {
                api.fetchAllAvailableClinics()
            } catch (e: Exception) {
                null
            }
        }?.map { Clinic(it) }

        if (clinics != null) {
            async { dao.renewWith(clinics) }
            clinics
        } else localClinic()
    }
}