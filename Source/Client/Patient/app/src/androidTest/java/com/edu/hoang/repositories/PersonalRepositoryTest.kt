package com.edu.hoang.repositories

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edu.hoang.store.PersonalRepository
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PersonalRepositoryTest {
    private val personalRepository = PersonalRepository.apply { id = 1 }

    @Test
    fun `get details and sync`() = runBlocking {
        val patient = personalRepository.getDetails()
        Assert.assertEquals("architect", patient.job)
    }


}