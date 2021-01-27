package com.edu.hoang

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edu.hoang.net.NetworkService
import com.edu.hoang.net.dto.TestDto
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class NetworkServiceTestDetails {
    private val testApi = NetworkService.testDetailsApi.value
    private val personalDetailsApi = NetworkService.personalDetailsApi.value
    private val prescriptionApi = NetworkService.prescriptionDetailsApi.value
    private val clinicApi = NetworkService.clinicDetailsApi.value

    @Test
    fun `test clinic all locations`() = runBlocking {
        val clinics = clinicApi.fetchAllAvailableClinics()
        assertEquals(3, clinics.size)
    }

    @Test
    fun `test clinic that a doctor is working for`() = runBlocking {
        val firstClinic = clinicApi.fetchClinicsOfDoctor(1)[0]
        assertEquals("02334545", firstClinic.contact)
    }

    @Test
    fun `test personal patient details`() = runBlocking {
        val patient = personalDetailsApi.fetchPatientDetails(1)
        assertEquals(178, patient.height)
    }

    @Test
    fun `test personal doctor details`() = runBlocking {
        val doctor = personalDetailsApi.fetchDoctorDetails(1)
        assertEquals("surgery", doctor.faculty)
    }

    @Test
    fun `test prescription latest`() = runBlocking {
        val firstInstruction = prescriptionApi.fetchLatestPrescription(1).instructions[0]
        assertEquals(3, firstInstruction.id)
    }

    @Test
    fun `test prescription all`() = runBlocking {
        val allPres = prescriptionApi.fetchAllPrescription(1)
        assertEquals(2, allPres.size)
    }

    @Test
    fun `test prescription all drugs`() = runBlocking {
        val drugs = prescriptionApi.fetchAllDrugTypes()
        assertEquals(4, drugs.size)
    }

    @Test
    fun `test prescription using drugs`() = runBlocking {
        val drugs = prescriptionApi.fetchUsingDrug(3)
        assertEquals(2, drugs.size)
    }

    @Test
    fun `test prescription specific drugs`() = runBlocking {
        val drug = prescriptionApi.fetchSpecificDrug(1)
        assertEquals("Insulin", drug.name)
    }

    @Test
    fun `test all available tests`() = runBlocking {
        val tests = testApi.fetchAllAvailableTests(1)
        assertTrue(tests.size >= 3)
    }

    @Test
    fun `test insert test`() = runBlocking {
        val dto = TestDto(0, Date(), "None", 1f, 1f, 1f, 1f, 1)
        testApi.pushNewTestResult(1, dto)
    }
}