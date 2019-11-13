package com.legion1900.mvvmnews

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.legion1900.mvvmnews.models.repository.impl.NewsCache
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsCacheTest {

    @After
    fun onClearDb() {
        newsCache.clearCache()
    }

    @Test
    fun writeArticles_test() {
        newsCache.writeArticles(articles)
    }

    @Test
    fun readArticles_test() {
        newsCache.writeArticles(articles)
        val articles = newsCache.readArticles(1..20)
        assertEquals("Lists should be the same", Data.articles, articles)
    }

    private companion object Data {

        @JvmStatic
        lateinit var newsCache: NewsCache

        @JvmStatic
        private val articles = mutableListOf<ArticleEntity>()

        @BeforeClass
        @JvmStatic
        fun onDataSetup() {
            val author = "Author"
            val title = "Title"
            val pubAt = "01-01-2020"
            val source = "Source"
            val url = "https://example.com"
            val desc = "Foo Bar"
            for (id in 1..20) {
                articles += ArticleEntity(id, author, title, pubAt, source, url, desc)
            }
        }

        @BeforeClass
        @JvmStatic
        fun onDbSetup() {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            newsCache = NewsCache(appContext)
        }
    }
}