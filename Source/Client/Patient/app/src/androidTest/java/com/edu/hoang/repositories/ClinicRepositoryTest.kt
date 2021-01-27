package com.edu.hoang.repositories

import com.edu.hoang.net.NetworkService
import com.edu.hoang.store.ClinicRepository
import com.edu.hoang.store.DataInitializer
import kotlinx.coroutines.runBlocking
import org.junit.*

class ClinicRepositoryTest {

    private lateinit var repository: ClinicRepository

    @Before
    fun `init data`() = runBlocking {
        val initializer = DataInitializer().apply { run().join() }
        repository = ClinicRepository(
            NetworkService.clinicDetailsApi.value, initializer.clinicDao
        )
    }

    @Test
    fun `reload local without Internet`() = runBlocking {
        val total = repository.reload().size
        Assert.assertEquals(2, total)
    }
}