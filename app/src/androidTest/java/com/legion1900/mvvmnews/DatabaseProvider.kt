package com.legion1900.mvvmnews

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.legion1900.mvvmnews.models.room.database.CacheDatabase

object DatabaseProvider {
    val context = InstrumentationRegistry.getInstrumentation().targetContext

    fun provideNewsCacheDb(): CacheDatabase {
        val db =
            Room.databaseBuilder(context, CacheDatabase::class.java, CacheDatabase.DB_NAME)
                .build()
        return db
    }
}