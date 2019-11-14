package com.legion1900.mvvmnews.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.repository.impl.NewsCache
import com.legion1900.mvvmnews.util.DataProvider
import com.legion1900.mvvmnews.util.DataProvider.TOPICS
import com.legion1900.mvvmnews.util.DatabaseProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class NewsCacheTest {
    private val newsCache = NewsCache(DatabaseProvider.context)

    private lateinit var date: Date

    @Before
    fun onDateSetup() {
        date = Date(Random.nextLong())
    }

    @Test
    fun writeArticles_test() {
        populate()
        assertArticles()
        newsCache.clearCache()
    }

    @Test
    fun writeArticles_override_test() {
        val num = 2
        for (i in 0 until num)
            populate()
        assertArticles()
        newsCache.clearCache()
    }

    private fun populate() {
        for ((topic, articles) in articles) {
            newsCache.writeArticles(topic, date, articles)
        }
    }

    private fun assertArticles() {
        for ((topic, articles) in articles) {
            val cache = newsCache.readArticles(topic)
            assertEquals(cache, articles)
        }
    }

    @Test
    fun clearCache_test() {
        populate()
        newsCache.clearCache()

        for (topic in TOPICS)
            assertTrue(
                "Article table should be empty after clearCache() call",
                newsCache.readArticles(topic).isEmpty()
            )
    }

    companion object Data {
        private const val ARTICLE_PER_TOPIC = 20

        @JvmStatic
        val articles = HashMap<String, List<Article>>()

        @BeforeClass
        @JvmStatic
        fun onDataSetup() {
            for (topic in TOPICS) {
                val articles = DataProvider.buildArticleList(ARTICLE_PER_TOPIC)
                this.articles += topic to articles
            }
        }
    }
}