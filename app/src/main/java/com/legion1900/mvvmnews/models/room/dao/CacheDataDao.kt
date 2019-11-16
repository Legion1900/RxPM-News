package com.legion1900.mvvmnews.models.room.dao

import androidx.room.*
import com.legion1900.mvvmnews.models.room.entity.CacheDataEntity
import java.util.*

@Dao
interface CacheDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: CacheDataEntity)

    /*
    * Returns null if executed on empty table.
    * */
    @Query("SELECT * FROM CacheData WHERE topic = :topic LIMIT 1")
    fun getCacheFor(topic: String): CacheDataEntity?

    /*
    * Returns null if executed on empty table.
    * */
    @Query("SELECT date FROM CacheData WHERE topic = :topic LIMIT 1")
    fun getDateFor(topic: String): Date?

    @Query("SELECT * FROM CacheData")
    fun getAllData(): List<CacheDataEntity>

    @Query("DELETE FROM CacheData WHERE topic = :topic")
    fun deleteDataFor(topic: String)

    @Query("DELETE FROM CacheData")
    fun clear()
}