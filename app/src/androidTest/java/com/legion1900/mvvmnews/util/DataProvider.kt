package com.legion1900.mvvmnews.util

import com.legion1900.mvvmnews.R
import com.legion1900.mvvmnews.models.DataConverter
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity
import com.legion1900.mvvmnews.models.room.entity.CacheDataEntity
import java.util.*
import kotlin.random.Random

object DataProvider {
    val TOPICS: Array<String> = DatabaseProvider.context.resources.getStringArray(R.array.topics)

    private const val AUTHOR = "Author"
    private const val TITLE = "Title"
    private const val PUB_AT = "01-01-2020"
    private const val SOURCE = "Source"
    private const val URL = "https://example.com"
    private const val DESC = "Foo Bar"

    fun buildDefaultCacheEntities(): List<CacheDataEntity> {
        val entities = mutableListOf<CacheDataEntity>()
        for (topic in TOPICS)
            entities += CacheDataEntity(topic, getRandomDate())
        return entities
    }

    private fun getRandomDate() = Date(Random.nextLong())

    fun buildArticleEntities(num: Int): List<ArticleEntity> {
        val articles = mutableListOf<Article>()
        for (i in 0 until num)
            articles += buildArticle()
        return DataConverter.articlesToEntities(articles)
    }

    fun buildDefaultArticleEntities(num: Int): List<ArticleEntity> {
        val entities = mutableListOf<ArticleEntity>()
        for (topic in TOPICS) {
            val articles = buildArticleList(num)
            entities += DataConverter.articlesToEntities(articles, topic)
        }
        return entities
    }

    private fun buildArticle() = Article(AUTHOR, TITLE, PUB_AT, SOURCE, URL, DESC)

    private fun buildArticleList(num: Int): List<Article> {
        val list = mutableListOf<Article>()
        for (i in 0 until num)
            list += buildArticle()
        return list
    }
}