package com.money.soypobre.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateFormatter {
    private val formatters = mutableMapOf<String, SimpleDateFormat>()

    fun format(
        timestamp: Long,
        format: String = "dd/MM/yyyy"
    ) : String = formatters.getOrPut(format) {
        SimpleDateFormat(
            format,
            Locale.getDefault()
        ).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
    }.format(Date(timestamp))
}