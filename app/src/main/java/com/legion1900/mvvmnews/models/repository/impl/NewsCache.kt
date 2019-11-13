package com.legion1900.mvvmnews.models.repository.impl

import android.content.Context
import androidx.room.Room
import com.legion1900.mvvmnews.models.dao.ArticleDao
import com.legion1900.mvvmnews.models.data.entities.Article
import com.legion1900.mvvmnews.models.database.CacheDatabase
import com.legion1900.mvvmnews.models.repository.abs.CacheRepository
import com.legion1900.mvvmnews.utils.toIntArray

class NewsCache(appContext: Context) : CacheRepository {
    private val dao: ArticleDao

    init {
        val db = Room.databaseBuilder(appContext, CacheDatabase::class.java, CacheDatabase.DB_NAME)
            .build()
        dao = db.articleDao()
    }

    override fun writeArticles(articles: List<Article>) {
        dao.insert(*articles.toTypedArray())
    }

    override fun readArticles(ids: IntRange): List<Article> = dao.getArticlesFor(ids.toIntArray())

    override fun clearCache() {
        dao.clear()
    }
}
