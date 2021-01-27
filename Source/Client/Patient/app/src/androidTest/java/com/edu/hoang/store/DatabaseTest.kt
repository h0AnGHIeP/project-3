package com.edu.hoang.store

import com.edu.hoang.store.dao.*
import com.edu.hoang.store.data.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class DatabaseTest {

    private lateinit var clinicDao: ClinicDetailsDataAccess
    private lateinit var prescriptionDao: PrescriptionDataAccess
    private lateinit var drugDao: DrugTypeDataAccess
    private lateinit var testDao: TestDetailsDataAccess
    private lateinit var remindDao: RemindTimeDataAccess

    @Before
    fun initDatabase() {
        val initializer = DataInitializer()
        runBlocking {
            initializer.run().join()
        }
        clinicDao = initializer.clinicDao
        prescriptionDao = initializer.prescriptionDao
        drugDao = initializer.drugDao
        testDao = initializer.testDao
        remindDao = initializer.remindDao
    }

    @Test
    fun `test clinic findAll`() = runBlocking {
        assertEquals(2, clinicDao.findAll().size)
    }

    @Test
    fun `test clinic nearest`() = runBlocking {
        assertEquals(1.0f, clinicDao.findNearest(1.2f, 1.2f).latitude)
    }

    @Test
    fun `test clinic delete`() = runBlocking {
        val clinic = Clinic(1)
        clinicDao.delete(listOf(clinic))
        assertEquals(1, clinicDao.findAll().size)
    }

    @Test
    fun `test clinic`() = runBlocking {
        val clinic = Clinic(1, longitude = 1.5f, latitude = 1.5f)
        clinicDao.update(listOf(clinic))
        assertEquals(1.5f, clinicDao.findById(1).latitude)
    }


    @Test
    fun `test prescription findAll`() = runBlocking {
        val total = prescriptionDao.findAll().size
        assertEquals(2, total)
    }

    @Test
    fun `test prescription instruction`() = runBlocking {
        val totalInstructions = prescriptionDao.findById(1)!!.instructions.size
        assertEquals(3, totalInstructions)
    }

    @Test
    fun `test prescription findLatest`() = runBlocking {
        val total = prescriptionDao.findLatest()
        assertEquals(2, total!!.prescription.id)
    }

    @Test
    fun `test save instructions`() = runBlocking {
        prescriptionDao.saveInstructions(
            listOf(Instruction(5, prescriptionId = 2))
        )
        assertEquals(2, prescriptionDao.findById(2)!!.instructions.size)
    }

    @Test
    fun `test drug instructions`() = runBlocking {
        val total = drugDao.findAll().size
        assertEquals(3, total)
    }

    @Test
    fun `test drug find`() = runBlocking {
        val name = drugDao.findAll()[0].name
        assertEquals("Insulin", name)
    }

    @Test
    fun `test TestDao`() = runBlocking {
        val total = testDao.findAll().size
        assertEquals(3, total)
    }

    @Test
    fun `test find limit`() = runBlocking {
        val tests = testDao.findLimit(2)
        assertEquals(2, tests.size)
        assertEquals(300, tests[0].time.time)
    }

    @Test
    fun `test insert`() = runBlocking {
        testDao.save(TestDetails(description = "New Test"))
        val retrieve = testDao.findLimit(1)[0]
        assertEquals("New Test", retrieve.description)
        assertNotEquals(1, retrieve.id)
        assertNotEquals(2, retrieve.id)
        assertNotEquals(3, retrieve.id)
    }

    @Test
    fun `test remind find all`() = runBlocking {
        val total = remindDao.findAll().size
        assertEquals(4, total)
    }

    @Test
    fun `test remind find recent`() = runBlocking {
        val details = remindDao.findRecentRemindTime(1)
        val total = details.size
        assertEquals(3, total)
        val c = Calendar.getInstance(Locale.US).apply {
            timeInMillis = details[1].remindTimes[0].time!!.time
        }
        assertTrue( details[1].remindTimes[0].active)
        assertEquals(2, c.get(Calendar.HOUR))
    }
}