package com.legion1900.mvvmnews.models.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Article",
    foreignKeys = [ForeignKey(
        entity = CacheDataEntity::class,
        parentColumns = ["topic"],
        childColumns = ["topic"],
        onDelete = CASCADE
    )],
    indices = [Index(name = "topic_index", value = ["topic"], unique = false)]
)
data class ArticleEntity(
    val author: String?,
    val title: String?,
    val publishedAt: String?,
    val sourceName: String?,
    val urlToImage: String?,
    val description: String?,
    var topic: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)