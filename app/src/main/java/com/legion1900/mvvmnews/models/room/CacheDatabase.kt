package com.legion1900.mvvmnews.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.legion1900.mvvmnews.models.data.Article

@Database(entities = [Article::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}