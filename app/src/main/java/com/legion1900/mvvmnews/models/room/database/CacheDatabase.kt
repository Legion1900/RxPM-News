package com.legion1900.mvvmnews.models.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.legion1900.mvvmnews.models.room.dao.ArticleDao
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DB_NAME = "NewsCache"
    }
}