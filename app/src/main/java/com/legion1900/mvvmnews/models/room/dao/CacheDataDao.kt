package com.legion1900.mvvmnews.models.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.legion1900.mvvmnews.models.room.entity.CacheDataEntity
import java.util.*

@Dao
interface CacheDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: CacheDataEntity)

    @Query("SELECT * FROM CacheData WHERE topic = :topic LIMIT 1")
    fun getCacheFor(topic: String): CacheDataEntity

    @Query("SELECT date FROM CacheData WHERE topic = :topic LIMIT 1")
    fun getDateFor(topic: String): Date

    @Query("SELECT * FROM CacheData")
    fun getAllData(): List<CacheDataEntity>

    @Query("DELETE FROM CacheData")
    fun clear()
}