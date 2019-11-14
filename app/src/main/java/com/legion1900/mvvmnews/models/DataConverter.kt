package com.legion1900.mvvmnews.models

import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity

object DataConverter {
    fun articlesToEntities(articles: List<Article>, topic: String? = null): List<ArticleEntity> {
        val entities = mutableListOf<ArticleEntity>()
        for (a in articles)
            entities += ArticleEntity(a, topic)
        return entities
    }
}