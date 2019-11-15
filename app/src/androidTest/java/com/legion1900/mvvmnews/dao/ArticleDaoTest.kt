package com.legion1900.mvvmnews.dao

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.room.dao.ArticleDao
import com.legion1900.mvvmnews.models.room.dao.CacheDataDao
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity
import com.legion1900.mvvmnews.util.DataProvider
import com.legion1900.mvvmnews.util.DataProvider.TOPICS
import com.legion1900.mvvmnews.util.DatabaseProvider
import com.legion1900.mvvmnews.util.blockingValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @After
    fun onClearDb() {
        articleDao.clear()
    }

    @Test
    fun insert_nullTopic_test() {
        articleDao.insert(*nullTopicEntities.toTypedArray())
        val articles: LiveData<List<Article>> = articleDao.getAllArticles()
        Log.d("articles", articles.value.toString())
        assertArticles(nullTopicEntities, articles.blockingValue, "id", "topic")
    }

    @Test
    fun insert_normalArticles_test() {
        val cache = DataProvider.buildDefaultCacheEntities()
        for (c in cache)
            cacheDao.update(c)
        val articles = mutableMapOf<String, LiveData<List<Article>>>()
        for (topic in TOPICS) {
            articles[topic] = articleDao.getArticlesFor(topic)
        }

        articleDao.insert(*defaultArticles.toTypedArray())
        val groups: Map<String?, List<ArticleEntity>> = defaultArticles.groupBy { it.topic }
        for (topic in TOPICS) {
            val real = articles.getValue(topic).blockingValue
            val expected = groups.getValue(topic)
            assertArticles(expected, real, "id")
        }
    }

    private fun assertArticles(
        expected: List<ArticleEntity>,
        real: List<Article>,
        vararg ignoreFields: String
    ) {
        for (i in expected.indices)
            assertThat(expected[i].article)
                .isEqualToIgnoringGivenFields(real[i], *ignoreFields)
    }

    companion object Data {

        private const val ROW_NUM = 20

        @JvmStatic
        lateinit var articleDao: ArticleDao
        @JvmStatic
        lateinit var cacheDao: CacheDataDao

        /*
        * Articles with null topic.
        * */
        private val nullTopicEntities: List<ArticleEntity> =
            DataProvider.buildArticleEntities(ROW_NUM)

        /*
        * Articles for default topics;
        * list contains 20 articles for each topic.
        * */
        private val defaultArticles: List<ArticleEntity> =
            DataProvider.buildDefaultArticleEntities(ROW_NUM)

        @BeforeClass
        @JvmStatic
        fun onDbSetup() {
            val db = DatabaseProvider.provideNewsCacheDb()
            articleDao = db.articleDao()
            cacheDao = db.cacheDataDao()
        }

        @AfterClass
        @JvmStatic
        fun onCleanup() {
            cacheDao.clear()
        }
    }
}