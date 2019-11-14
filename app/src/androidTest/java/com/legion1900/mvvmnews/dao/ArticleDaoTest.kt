package com.legion1900.mvvmnews.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.room.dao.ArticleDao
import com.legion1900.mvvmnews.models.room.dao.CacheDataDao
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity
import com.legion1900.mvvmnews.util.DataProvider
import com.legion1900.mvvmnews.util.DataProvider.TOPICS
import com.legion1900.mvvmnews.util.DatabaseProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    @After
    fun onClearDb() {
        articleDao.clear()
    }

    @Test
    fun insert_nullTopic_test() {
        articleDao.insert(*nullTopicEntities.toTypedArray())
        val articles = articleDao.getAllArticles()
        assertArticles(nullTopicEntities, articles, "id", "topic")
    }

    @Test
    fun insert_normalArticles_test() {
        val cache = DataProvider.buildDefaultCacheEntities()
        for (c in cache)
            cacheDao.update(c)
        articleDao.insert(*defaultArticles.toTypedArray())
        val groups: Map<String?, List<ArticleEntity>> = defaultArticles.groupBy { it.topic }
        for (topic in TOPICS) {
            val articles = articleDao.getArticlesFor(topic)
            val expected = groups.getValue(topic)
            assertArticles(expected, articles, "id")
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
        private val nullTopicEntities: List<ArticleEntity> = DataProvider.buildArticleEntities(ROW_NUM)

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
    }
}