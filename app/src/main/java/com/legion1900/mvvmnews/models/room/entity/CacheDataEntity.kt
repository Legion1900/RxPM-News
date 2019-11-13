package com.legion1900.mvvmnews.models.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "CacheData")
data class CacheDataEntity (
    @PrimaryKey val topic: String,
    val date: Date
)