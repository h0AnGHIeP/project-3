package com.edu.hoang

import com.edu.hoang.store.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.sql.Time
import java.util.*

class TimeUtilsTest {
    @Test
    fun `test from calendar`() {
        val c = getCalendar(10, 32)
        assertEquals(10, c.get(Calendar.HOUR_OF_DAY))
        assertEquals(32, c.get(Calendar.MINUTE))
    }

    @Test
    fun `test time sql`() {
        val timeMillis = getCalendar(10, 32)
        val sqlTime = Time(timeMillis.timeInMillis)
        val c = sqlTime.toCalendar()
        assertEquals(10, c.get(Calendar.HOUR_OF_DAY))
        assertEquals(32, c.get(Calendar.MINUTE))
    }

    @Test
    fun `time from hour and minute`() {
        val sqlTime = getTimeFrom(10, 32)
        assertEquals(10, sqlTime.toCalendar().get(Calendar.HOUR_OF_DAY))
        assertEquals(32, sqlTime.toCalendar().get(Calendar.MINUTE))
    }

    @Test
    fun `time to hour and minute`() {
        val sqlTime = getTimeFrom(10, 32)
        assertEquals(10, sqlTime.getHour())
        assertEquals(32, sqlTime.getMinute())
    }

    @Test
    fun `calculate age`() {
        val myBirth = Calendar.getInstance(Locale.US).run {
            set(1989, 1, 2)
            timeInMillis
        }
        val myAge = ageFrom(Date(myBirth))
        assertEquals(32, myAge)
    }

}