package com.legion1900.mvvmnews.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.repository.impl.CacheRepo
import com.legion1900.mvvmnews.util.DataProvider
import com.legion1900.mvvmnews.util.DataProvider.TOPICS
import com.legion1900.mvvmnews.util.DatabaseProvider
import com.legion1900.mvvmnews.util.blockingValue
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.*
import org.junit.runner.RunWith
import java.util.*
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class CacheRepoTest {


    val mockCallback: () -> Unit = mock()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    private val newsCache = CacheRepo(DatabaseProvider.context)

    private lateinit var date: Date

    @Before
    fun onDateSetup() {
        date = Date(Random.nextLong())
    }

    @After
    fun onClear() {
        newsCache.clearCache()
    }

    @Test
    fun writeArticles_test() {
        populate()
        assertArticles()
    }

    @Test
    fun writeArticles_override_test() {
        val num = 2
        for (i in 0 until num)
            populate()
        assertArticles()
    }

    /*
    * Tests if LiveData triggers only on same row changed.
    * Expected call numbers:
    *   --  one immediately after observer attached
    *   --  two for each call to writeArticle = 1 for deletion of old articles + 1 for insertion
    * */
    @Test
    fun writeArticles_liveData_test() {
        val topic1 = TOPICS[0]
        val articles = newsCache.readArticles(topic1)
        populate() // One call expected on observer attach.
        articles.observeForever {
            mockCallback()
        }
        populate() // Two calls expected: on deletion + on insertion.
        verify(mockCallback, times(3)).invoke()
    }

    @Test
    fun clearCache_test() {
        populate()
        newsCache.clearCache()

        for (topic in TOPICS)
            assertTrue(
                "Article table should be empty after clearCache() call",
                newsCache.readArticles(topic).blockingValue.isEmpty()
            )
    }

    private fun populate() {
        for ((topic, articles) in articles) {
            newsCache.writeArticles(topic, date, articles)
        }
    }

    private fun assertArticles() {
        for ((topic, articles) in articles) {
            val cache: LiveData<List<Article>> = newsCache.readArticles(topic)
            assertThat(articles).isEqualTo(cache.blockingValue)
        }
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