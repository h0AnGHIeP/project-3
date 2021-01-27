package com.edu.hoang.store

import com.edu.hoang.net.NetworkService
import com.edu.hoang.net.NetworkService.NETWORK_TIMEOUT
import com.edu.hoang.net.TestDetailsApi
import com.edu.hoang.store.dao.TestDetailsDataAccess
import com.edu.hoang.store.data.TestDetails
import kotlinx.coroutines.*

class TestResultRepository(
    private val api: TestDetailsApi = NetworkService.testDetailsApi.value,
    private val dao: TestDetailsDataAccess = LocalStorageService.testDao.value
) {
    private val personalId = PersonalRepository.id

    suspend fun createNewTestResult(
        desc: String = "",
        hba1c: Float = 0F,
        random: Float = 0F,
        craving: Float = 0F,
        afterMeal: Float = 0F
    ) = coroutineScope {
        val test = TestDetails(
            description = desc,
            hba1cIndex = hba1c,
            randomIndex = random,
            cravingIndex = craving,
            afterMealIndex = afterMeal
        )
        async { dao.save(test) }
        async { pushNewTest(test) }
        test
    }

    suspend fun localHistory() = dao.findAll()

    suspend fun reloadLocal() = coroutineScope {
        val results = withTimeoutOrNull(NETWORK_TIMEOUT) {
            try {
                api.fetchAllAvailableTests(personalId)
            } catch (e: Exception) {
                null
            }
        }?.map { TestDetails(it) }
        if (results != null) {
            async {
                val local = localHistory()
                if (results.size != local.size) {
                    dao.renewWith(local)
                }
            }
            results
        } else localHistory()
    }

    private suspend fun pushNewTest(details: TestDetails) {
        withTimeout(NETWORK_TIMEOUT) {
            try {
                api.pushNewTestResult(personalId, details.dto(personalId))
            } finally {
            }
        }
    }
}