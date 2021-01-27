package com.edu.hoang.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.edu.hoang.store.*
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("textLong")
fun TextView.setTextLong(number: Long) {
    text = number.toString()
}

@BindingAdapter("textFloat")
fun TextView.setTextFloat(number: Float) {
    text = number.toString()
}

@BindingAdapter("textDate")
fun TextView.setTextDate(date: Date?) {
    if (date != null) {
        text = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH)
            .format(date)
    }
}

@BindingAdapter("textInt")
fun TextView.setTextInt(number: Int) {
    text = number.toString()
}

@BindingAdapter("textBool")
fun TextView.setTextBool(boolean: Boolean) {
    text = if (boolean) "Yes" else "No"
}

@BindingAdapter("textTime")
fun TextView.setTextTime(time: Time) {
    val hour = time.getHour()
    val min = time.getMinute()
    val formatted = formatTime(hour, min)
    text = formatted
}