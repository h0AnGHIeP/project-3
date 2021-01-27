package com.edu.hoang.store

import androidx.room.TypeConverter
import java.sql.Time
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(date: Date) = date.time


    @TypeConverter
    fun toTimestamp(millis: Long) = Date(millis)

    @TypeConverter
    fun fromBool(bool: Boolean) = if (bool) "true" else "false"

    @TypeConverter
    fun toBool(str: String) = str.startsWith("t", true)

    @TypeConverter
    fun fromTime(time: Time) = time.time

    @TypeConverter
    fun toTime(timeInMillis: Long) = Time(timeInMillis)
}

