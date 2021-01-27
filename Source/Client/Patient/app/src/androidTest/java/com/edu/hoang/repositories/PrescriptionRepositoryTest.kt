package com.edu.hoang.repositories

import com.edu.hoang.net.NetworkService.prescriptionDetailsApi
import com.edu.hoang.store.DataInitializer
import com.edu.hoang.store.PrescriptionRepository
import kotlinx.coroutines.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class PrescriptionRepositoryTest {
    private val repository = PrescriptionRepository()

    private lateinit var mockedDataRepository: PrescriptionRepository

    private lateinit var testJob: Job

    @Before
    fun `initialize mock data repository`() {
        val initializer = DataInitializer()
        testJob = initializer.run()
        mockedDataRepository = PrescriptionRepository(
            prescriptionDetailsApi.value, initializer.prescriptionDao, initializer.drugDao
        )
    }

    private fun <T> runAwaitJob(block: suspend CoroutineScope.() -> T) = runBlocking {
        testJob.join()
        block()
    }


    @Test
    fun `get empty Prescription`() = runAwaitJob {
        val id = repository.latestPrescription().prescription.id
        assertEquals(0, id)
    }

    @Test
    fun `get local Prescription`() = runAwaitJob {
        val id = mockedDataRepository.latestPrescription().prescription.id
        assertEquals(2, id)
    }

    @Test
    fun `get specific Prescription`() = runAwaitJob {
        val id = mockedDataRepository.specificPrescriptionOrDefault(1).prescription.id
        assertEquals(1, id)
    }

    @Test
    fun `get local history`() = runAwaitJob {
        val total = mockedDataRepository.allLocalPrescription().size
        assertEquals(2, total)
    }

    @Test
    fun `reload local history`() = runAwaitJob {
        val total = mockedDataRepository.reloadHistory().size
        assertTrue(total >= 1)
    }

    @Ignore("Run in specific case")
    @Test
    fun `test local history`() = runAwaitJob {
        val id = repository.localLatestPrescriptionOrDefault().prescription.id
        assertEquals(3, id)
    }
}