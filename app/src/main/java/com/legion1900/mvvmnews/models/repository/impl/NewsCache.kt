package com.legion1900.mvvmnews.models.repository.impl

import android.content.Context
import androidx.room.Room
import com.legion1900.mvvmnews.models.repository.abs.CacheRepository
import com.legion1900.mvvmnews.models.room.dao.ArticleDao
import com.legion1900.mvvmnews.models.room.database.CacheDatabase
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity
import com.legion1900.mvvmnews.utils.toIntArray

class NewsCache(appContext: Context) : CacheRepository {
    private val dao: ArticleDao

    init {
        val db = Room.databaseBuilder(appContext, CacheDatabase::class.java, CacheDatabase.DB_NAME)
            .build()
        dao = db.articleDao()
    }

    override fun writeArticles(articles: List<ArticleEntity>) {
        dao.insert(*articles.toTypedArray())
    }

    override fun readArticles(ids: IntRange): List<ArticleEntity> =
        dao.getArticlesFor(ids.toIntArray())

    override fun clearCache() {
        dao.clear()
    }
}
