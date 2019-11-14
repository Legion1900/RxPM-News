package com.legion1900.mvvmnews

import com.legion1900.mvvmnews.models.room.entity.ArticleEntity

object DataProvider {
    val TOPICS = DatabaseProvider.context.resources.getStringArray(R.array.topics)

    private const val AUTHOR = "Author"
    private const val TITLE = "Title"
    private const val PUB_AT = "01-01-2020"
    private const val SOURCE = "Source"
    private const val URL = "https://example.com"
    private const val DESC = "Foo Bar"

    fun buildArticles(num: Int, topic: String? = null): List<ArticleEntity> {
        val articles = mutableListOf<ArticleEntity>()
        for (i in 0 until num)
            articles += buildArticle(topic)
        return articles
    }

    fun buildDefaultArticles(num: Int): List<ArticleEntity> {
        val articles = mutableListOf<ArticleEntity>()
        for (topic in TOPICS)
            articles += buildArticles(num, topic)
        return articles
    }

    private fun buildArticle(topic: String?) =
        ArticleEntity(AUTHOR, TITLE, PUB_AT, SOURCE, URL, DESC, topic)
}