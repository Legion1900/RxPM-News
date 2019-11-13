package com.legion1900.mvvmnews.models.repository.abs

import com.legion1900.mvvmnews.models.room.entity.ArticleEntity

interface CacheRepository {
    // TODO: change signature to write articles FOR SPECIFIED topic AND date
    fun writeArticles(articles: List<ArticleEntity>)
    fun readArticles(ids: IntRange): List<ArticleEntity>
    fun clearCache()
}