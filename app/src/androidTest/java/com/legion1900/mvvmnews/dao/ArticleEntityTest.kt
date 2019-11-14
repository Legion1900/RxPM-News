package com.legion1900.mvvmnews.dao

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.legion1900.mvvmnews.models.room.dao.ArticleDao
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity
import com.legion1900.mvvmnews.util.DataProvider
import com.legion1900.mvvmnews.util.DatabaseProvider
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

        Log.d("insert_test", "null topic articles: ${dao.getAllArticles()}")
    }

    @Test
    fun insert_normalArticles_test() {
        dao.insert(*defaultArticles.toTypedArray())

        Log.d("insert_test", "default articles: ${dao.getAllArticles()}")
    }

    @Test
    fun getArticlesFor_test() {
        dao.insert(*defaultArticles.toTypedArray())
        val articles = mutableListOf<ArticleEntity>()
        for (topic in DataProvider.TOPICS)
            articles += dao.getArticlesFor(topic)

        Log.d("select_test", "get for test: $articles")
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