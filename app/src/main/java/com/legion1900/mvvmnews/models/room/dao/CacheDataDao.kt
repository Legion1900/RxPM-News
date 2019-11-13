package com.legion1900.mvvmnews.models.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.legion1900.mvvmnews.models.room.entity.CacheDataEntity

@Dao
interface CacheDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: CacheDataEntity)

    @Query("SELECT * FROM CacheData WHERE topic = :topic LIMIT 1")
    fun getDataFor(topic: String): CacheDataEntity

    @Query("DELETE FROM CacheData")
    fun clear()
}