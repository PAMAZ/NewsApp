package it.paolomazza.newsapp.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun Long.timestampToDate(): String {
        val date = Date(this)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.ITALY)
        return format.format(date)
    }
}