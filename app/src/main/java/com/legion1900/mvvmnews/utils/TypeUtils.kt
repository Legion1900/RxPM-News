package com.legion1900.mvvmnews.utils

import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity

fun IntRange.toIntArray(): IntArray {
    val array = IntArray(this.count())
    val iterator = iterator()
    for (i in array.indices)
        array[i] = iterator.nextInt()
    return array
}

fun articlesToEntities(articles: List<Article>, topic: String? = null): List<ArticleEntity> {
    val entities = mutableListOf<ArticleEntity>()
    for (a in articles)
        entities += ArticleEntity(a, topic)
    return entities
}