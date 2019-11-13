package com.legion1900.mvvmnews

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.legion1900.mvvmnews.models.room.dao.ArticleDao
import com.legion1900.mvvmnews.models.room.database.CacheDatabase
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity
import com.legion1900.mvvmnews.utils.toIntArray
import org.junit.After
import org.junit.Assert.assertEquals
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
    fun insert_test() {
        populateTable()
    }

    @Test
    fun getArticlesFor_test() {
        populateTable()
        val ids = ARTICLE_RANGE.toIntArray()
        val articles = dao.getArticlesFor(ids)
        assertEquals("Lists should be the same", Data.articles, articles)
    }

    private fun populateTable() {
        dao.insert(*articles.toTypedArray())
    }

    private companion object Data {

        val ARTICLE_RANGE = 1..20

        @JvmStatic
        lateinit var dao: ArticleDao

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
            val db =
                Room.databaseBuilder(appContext, CacheDatabase::class.java, CacheDatabase.DB_NAME)
                    .build()
            dao = db.articleDao()
        }
    }
}