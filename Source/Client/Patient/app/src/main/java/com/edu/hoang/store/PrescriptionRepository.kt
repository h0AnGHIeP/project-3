package com.edu.hoang.store

import com.edu.hoang.net.NetworkService
import com.edu.hoang.net.NetworkService.NETWORK_TIMEOUT
import com.edu.hoang.net.PrescriptionDetailsApi
import com.edu.hoang.net.dto.PrescriptionDto
import com.edu.hoang.store.dao.DrugTypeDataAccess
import com.edu.hoang.store.dao.PrescriptionDataAccess
import com.edu.hoang.store.data.*
import com.edu.hoang.store.relation.PrescriptionWithInstructions
import kotlinx.coroutines.*

class PrescriptionRepository(
    private val api: PrescriptionDetailsApi = NetworkService.prescriptionDetailsApi.value,
    private val prescriptionDao: PrescriptionDataAccess = LocalStorageService.prescriptionDao.value,
    private val drugDao: DrugTypeDataAccess = LocalStorageService.drugDao.value
) {

    private val personalId = PersonalRepository.id
    private var cacheLoaded = false
    private var drugCache = listOf<DrugType>()

    class PrescriptionDetails(
        val prescription: Prescription,
        val instructions: List<Pair<Instruction, DrugType>>
    ) {
        constructor() : this(Prescription(0), listOf())

    }

    suspend fun localLatestPrescriptionOrDefault(): PrescriptionDetails = coroutineScope {
        val cacheLoading = loadDrugCacheAsync()
        val localPrescription = prescriptionDao.findLatest()
        cacheLoading.await()
        if (localPrescription != null) prescriptionDetailsFrom(localPrescription)
        else PrescriptionDetails()
    }

    suspend fun latestPrescription(): PrescriptionDetails = coroutineScope {
        val cacheLoading = loadDrugCacheAsync()
        val latestPrescription = fetchLatestPrescription()
        if (latestPrescription != null) {
            async { saveDtoPrescription(latestPrescription) }
            cacheLoading.await()
            prescriptionDetailsFrom(latestPrescription)
        } else {
            cacheLoading.await()
            localLatestPrescriptionOrDefault()
        }
    }

    suspend fun specificPrescriptionOrDefault(id: Long): PrescriptionDetails {
        val prescription = prescriptionDao.findById(id)
        return if (prescription != null) prescriptionDetailsFrom(prescription)
        else PrescriptionDetails()
    }

    suspend fun allLocalPrescription() = prescriptionDao.findAll()

    suspend fun reloadHistory() = coroutineScope {
        val prescriptions = withTimeoutOrNull(NETWORK_TIMEOUT) {
            try {
                api.fetchAllPrescription(personalId)
            } catch (e: Exception) {
                null
            }
        }?.map { Prescription(it) }

        if (prescriptions != null && prescriptions.isNotEmpty()) {
            async { prescriptionDao.save(prescriptions) }
        }
        prescriptions ?: allLocalPrescription()
    }

    private suspend fun fetchLatestPrescription(): PrescriptionDto? {
        return withTimeoutOrNull(NETWORK_TIMEOUT) {
            try {
                api.fetchLatestPrescription(personalId)
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun saveDtoPrescription(dto: PrescriptionDto) {
        val instructions = instructionsFrom(dto)
        val prescription = Prescription(dto)
        prescriptionDao.save(prescription)
        prescriptionDao.saveInstructions(instructions)
    }

    private fun instructionsFrom(dto: PrescriptionDto) = dto.instructions.map {
        Instruction(it, dto.id)
    }

    private suspend fun prescriptionDetailsFrom(dto: PrescriptionDto): PrescriptionDetails {
        val prescription = Prescription(dto)
        val instructionsWithDrugs = instructionsFrom(dto).map { it to getDrug(it.drugId) }
        return PrescriptionDetails(prescription, instructionsWithDrugs)
    }

    private suspend fun prescriptionDetailsFrom(relation: PrescriptionWithInstructions): PrescriptionDetails {
        val instructionsWithDrugs = relation.instructions.map { it to getDrug(it.drugId) }
        return PrescriptionDetails(relation.prescription, instructionsWithDrugs)
    }

    private suspend fun loadDrugCacheAsync() = coroutineScope {
        if (!cacheLoaded) async {
            drugCache = drugDao.findAll()
            cacheLoaded = true
        } else async {}
    }

    private suspend fun getDrug(drugId: Long): DrugType {
        val drugInCache = findDrugInCache(drugId)
        return if (drugInCache != null) drugInCache
        else {
            reloadDrugCache()
            findDrugInCache(drugId) ?: DrugType(drugId)
        }
    }

    private suspend fun reloadDrugCache() = coroutineScope {
        val drugs = fetchUsingDrugs()?.map { DrugType(it) }
        if (drugs != null) {
            drugCache = drugs
            async { drugDao.save(drugs) }
        }
    }

    private fun findDrugInCache(drugId: Long) = drugCache.find { it.id == drugId }

    private suspend fun fetchUsingDrugs() = withTimeoutOrNull(NETWORK_TIMEOUT) {
        try {
            api.fetchUsingDrug(personalId)
        } catch (e: Exception) {
            null
        }
    }

}