package com.legion1900.mvvmnews.models.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.legion1900.mvvmnews.models.data.Article

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
    val article: Article,
    var topic: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)