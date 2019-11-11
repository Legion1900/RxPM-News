package com.legion1900.mvvmnews.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {

    companion object {
        fun dateToFormatStr(date: Date, format: String): String {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            return dateFormat.format(date)
        }

        fun getCurrentDate() = Calendar.getInstance().time
    }

    private val tmp1 = Calendar.getInstance()
    private val tmp2 = Calendar.getInstance()

    /*
    * Subtracts second date from first.
    * */
    fun subtract(first: Date, second: Date): Long {
        tmp1.time = first
        tmp2.time = second
        return tmp1.timeInMillis - tmp2.timeInMillis
    }
}