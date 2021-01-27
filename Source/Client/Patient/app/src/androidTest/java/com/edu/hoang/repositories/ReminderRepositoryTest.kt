package com.edu.hoang.repositories

import com.edu.hoang.store.*
import com.edu.hoang.store.dao.RemindTimeDataAccess
import com.edu.hoang.store.data.RemindTime
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse

class ReminderRepositoryTest {
    private lateinit var repository: ReminderRepository
    private lateinit var dao: RemindTimeDataAccess

    @Before
    fun `initialize mock data repository`() = runBlocking {
        val initializer = DataInitializer().apply { run().join() }
        repository = ReminderRepository(
            initializer.remindDao, initializer.prescriptionDao
        )
        dao = initializer.remindDao
    }

    @Test
    fun `add Remind`() = runBlocking {
        repository.addRemind(1, 1, "Hello", 3)
        assertEquals(5, dao.findAll().size)
    }

    @Test
    fun `test deactivate`() = runBlocking {
        val remindTime = RemindTime(
            id = 10, time = getTimeFrom(10, 32), active = true, instructionId = 1
        )
        dao.save(remindTime)
        repository.deactivateRemind(remindTime)
        assertFalse(dao.findById(10).active)
    }

    @Test
    fun `test change remind time`() = runBlocking {
        val remindTime = RemindTime(
            id = 10,
            time = getTimeFrom(10, 32),
            active = true,
            note = "Hi",
            instructionId = 1
        )
        dao.save(remindTime)
        repository.changeReminder(remindTime, 11, 50, null)
        dao.findById(10).run {
            assertEquals(11, time!!.getHour())
            assertEquals(50, time!!.getMinute())
            assertEquals("Hi", note)
        }
    }


    @Test
    fun `test active remind time instances`() = runBlocking {
        val total = repository.activeRemindTimes(2).size
        assertEquals(1, total)
    }

    @Test
    fun `test current remind time instances`() = runBlocking {
        val total = repository.currentRemindTimes().size
        assertEquals(1, total)
    }

    @Ignore("Run in specific case")
    @Test
    fun `test current remind time based on prescription`() = runBlocking {
        val total = ReminderRepository().activeRemindTimes(3).size
        assertEquals(1, total)
    }
}