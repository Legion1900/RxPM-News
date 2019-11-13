package com.legion1900.mvvmnews.models.room

import androidx.room.TypeConverter
import java.util.*

class Converter {
    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun dateFromTimestamp(value: Long) = Date(value)
}