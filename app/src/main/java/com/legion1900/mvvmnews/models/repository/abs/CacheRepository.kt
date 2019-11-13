package com.legion1900.mvvmnews.models.repository.abs

import com.legion1900.mvvmnews.models.data.entities.Article

interface CacheRepository {
    // TODO: change signature to write articles FOR SPECIFIED topic AND date
    fun writeArticles(articles: List<Article>)
    fun readArticles(ids: IntRange): List<Article>
    fun clearCache()
}