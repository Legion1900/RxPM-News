package com.legion1900.mvvmnews.models.room

import androidx.room.TypeConverter
import com.legion1900.mvvmnews.models.data.Article
import java.util.*

class Converter {
    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun dateFromTimestamp(value: Long) = Date(value)

    @TypeConverter
    fun articleToString(article: Article) = Article.toDbString(article)

    @TypeConverter
    fun stringToArticle(str: String) = Article.fromString(str)
}