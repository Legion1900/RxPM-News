package com.legion1900.mvvmnews.models.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Article")
data class ArticleEntity (
    @PrimaryKey val id: Int,
    val author: String?,
    val title: String?,
    val publishedAt: String?,
    val sourceName: String?,
    val urlToImage: String?,
    val description: String?
)