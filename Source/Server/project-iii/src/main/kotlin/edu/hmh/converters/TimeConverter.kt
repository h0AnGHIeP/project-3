package edu.hmh.converters

import org.springframework.core.convert.converter.Converter
import java.sql.Time

class TimeConverter : Converter<String, Time> {
    override fun convert(source: String): Time? {
        return Time.valueOf(source)
    }
}