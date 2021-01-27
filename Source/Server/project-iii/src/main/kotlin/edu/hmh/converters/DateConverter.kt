package edu.hmh.converters

import org.springframework.core.convert.converter.Converter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter : Converter<String, Date> {

    private val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)

    override fun convert(source: String): Date? {
        return formatter.parse(source)
    }
}