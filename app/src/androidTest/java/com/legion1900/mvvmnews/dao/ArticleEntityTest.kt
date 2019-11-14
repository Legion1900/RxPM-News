package com.legion1900.mvvmnews.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.mvvmnews.models.room.dao.ArticleDao
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity
import com.legion1900.mvvmnews.util.DataProvider
import com.legion1900.mvvmnews.util.DatabaseProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleEntityTest {

    @After
    fun onClearDb() {
        dao.clear()
    }

    @Test
    fun insert_nullTopic_test() {
        dao.insert(*nullTopicArticles.toTypedArray())
        val articles = dao.getAllArticles()
        assertArticles(nullTopicArticles, articles, "id", "topic")
    }

    @Test
    fun insert_normalArticles_test() {
        dao.insert(*defaultArticles.toTypedArray())
        val real = dao.getAllArticles().groupBy { it.topic }
        val expected = defaultArticles.groupBy { it.topic }
        for ((topic, articles) in expected)
            assertArticles(articles, real.getValue(topic), "id")
    }

    @Test
    fun getArticlesFor_test() {
        dao.insert(*defaultArticles.toTypedArray())
        val articles = mutableListOf<ArticleEntity>()
        for (topic in DataProvider.TOPICS)
            articles += dao.getArticlesFor(topic)

        val real = articles.groupBy { it.topic }
        val expected = defaultArticles.groupBy { it.topic }
        for (topic in DataProvider.TOPICS) {
            assertArticles(expected.getValue(topic), real.getValue(topic), "id")
        }
    }

    private fun assertArticles(
        expected: List<ArticleEntity>,
        real: List<ArticleEntity>,
        vararg ignoreFields: String
    ) {
        for (i in expected.indices)
            assertThat(expected[i])
                .isEqualToIgnoringGivenFields(real[i], *ignoreFields)
    }

    companion object Data {

        private const val ROW_NUM = 20

        @JvmStatic
        lateinit var dao: ArticleDao

        /*
        * Articles with null topic.
        * */
        private val nullTopicArticles: List<ArticleEntity> = DataProvider.buildArticles(ROW_NUM)

        /*
        * Articles for default topics;
        * list contains 20 articles for each topic.
        * */
        private val defaultArticles: List<ArticleEntity> =
            DataProvider.buildDefaultArticles(ROW_NUM)

        @BeforeClass
        @JvmStatic
        fun onDbSetup() {
            dao = DatabaseProvider.provideNewsCacheDb().articleDao()
        }
    }
}