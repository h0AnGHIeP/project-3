package com.edu.hoang.store

import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

fun getCalendar(hour: Int, minute: Int): Calendar = Calendar.getInstance(Locale.US)
    .apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
    }

fun Time.toCalendar(): Calendar {
    val cal = Calendar.getInstance(Locale.US)
    cal.timeInMillis = this.time
    return cal
}

fun getTimeFrom(hour: Int, minute: Int): Time {
    val millis = getCalendar(hour, minute).timeInMillis
    return Time(millis)
}

fun Time.getHour() = toCalendar().get(Calendar.HOUR_OF_DAY)
fun Time.getMinute() = toCalendar().get(Calendar.MINUTE)


fun ageFrom(birth: Date): Int = Calendar.getInstance().apply {
    val timeInMillis = Date().time - birth.time
    time = Date(timeInMillis)
}.get(Calendar.YEAR) - 1969

fun formatTime(hour: Int, minute: Int) = "${hour}h : ${minute}m"
fun formatDate(date: Date) :String{
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.US)
    return formatter.format(date)
}