package com.legion1900.mvvmnews.models.repository.impl

import android.util.Log
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.repository.abs.NewsRepository
import com.legion1900.mvvmnews.models.repository.impl.network.NewsLoader
import com.legion1900.mvvmnews.models.repository.impl.network.NewsService
import com.legion1900.mvvmnews.utils.TimeUtils
import java.util.*
import java.util.concurrent.Executors

class NewsRepo(
    private val newsCache: NewsCache,
    override val onStartCallback: () -> Unit,
    override val onLoadedCallback: (List<Article>) -> Unit,
    override val onFailureCallback: () -> Unit
) : NewsRepository {

    private var currentTopic: String? = null
        set(value) {
            if (field != value)
                field = value
        }
    private lateinit var date: Date

    private val utils = TimeUtils()

    private val loader = NewsLoader(
        onResponse = {
            // At this point currentTopic must be set to real value.
            updateCache(it)
            readCache()
        },
        onFailure = onFailureCallback
    )

    private val executor = Executors.newSingleThreadExecutor()

    override fun loadNews(topic: String) {
        currentTopic = topic
        executor.submit{
            onStartCallback()
            if (isOutdated())
                startLoading()
            else
                readCache()
        }
    }

    override fun clearCache() {
        executor.submit { newsCache.clearCache()
        Log.d("Test", "Cache cleared!")
        }
    }

    private fun updateCache(articles: List<Article>) {
        executor.submit { newsCache.writeArticles(currentTopic!!, date, articles) }
    }

    private fun readCache() {
        executor.submit {
            val articles = newsCache.readArticles(currentTopic!!)
            onLoadedCallback(articles)
        }
    }

    private fun isOutdated(): Boolean {
        date = TimeUtils.getCurrentDate()
        return currentTopic?.let {
            val date = newsCache.lastModified(it)
            date?.let { utils.subtract(this.date, date) >= TIMEOUT }
        } ?: true
    }

    private fun startLoading() {
        val args = NewsService.buildQuery(currentTopic!!, date)
        loader.loadAsync(args)
    }

    companion object {
        /*
        * Time in milliseconds before cached articles becomes invalid.
        * */
        const val TIMEOUT = 60_000
    }
}