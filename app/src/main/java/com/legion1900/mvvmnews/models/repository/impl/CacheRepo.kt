package com.legion1900.mvvmnews.models.repository.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.legion1900.mvvmnews.models.DataConverter
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.repository.abs.CacheRepository
import com.legion1900.mvvmnews.models.room.database.CacheDatabase
import com.legion1900.mvvmnews.models.room.entity.CacheDataEntity
import java.util.*

class CacheRepo(appContext: Context) : CacheRepository {

    private val db =
        Room.databaseBuilder(appContext, CacheDatabase::class.java, CacheDatabase.DB_NAME).build()
    private val articleDao = db.articleDao()
    private val cacheDao = db.cacheDataDao()

    override fun writeArticles(topic: String, date: Date, articles: List<Article>) {
        updateCacheData(topic, date)
        val entities = DataConverter.articlesToEntities(articles, topic)
        articleDao.insert(*entities.toTypedArray())
    }

    private fun updateCacheData(topic: String, date: Date) {
        cacheDao.deleteDataFor(topic)
        val cache = CacheDataEntity(topic, date)
        cacheDao.update(cache)
    }

    override fun readArticles(topic: String): LiveData<List<Article>> =
        articleDao.getArticlesFor(topic).distinctUntilChanged()

    override fun lastModified(topic: String): Date = cacheDao.getDateFor(topic)

    override fun clearCache() {
        cacheDao.clear()
    }

    private fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> =
        Transformations.distinctUntilChanged(this)

}
