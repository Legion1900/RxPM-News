package com.legion1900.mvvmnews.models.repository.impl

import com.legion1900.mvvmnews.models.repository.abs.CacheModel
import com.legion1900.mvvmnews.models.repository.impl.network.AsyncLoader
import com.legion1900.mvvmnews.models.repository.impl.network.NewsService
import com.legion1900.mvvmnews.utils.TimeUtils
import java.util.*

class Cache(
    override val cacheRepo: CacheRepo,
    val onStart: () -> Unit,
    onFinished: () -> Unit,
    onFailure: () -> Unit
) : CacheModel {

    private lateinit var topic: String
    private lateinit var date: Date

    private val timeUtils = TimeUtils()

    private val loader = AsyncLoader(
        onResponse = {
            cacheRepo.writeArticles(topic, date, it.articles)
            onFinished()
        },
        onFailure = onFailure
    )

    override fun updateNewsFor(topic: String) {
        this.topic = topic
        date = TimeUtils.getCurrentDate()
        if (isOutdated())
            updateCache()
    }

    private fun isOutdated(): Boolean {
        val timestamp = cacheRepo.lastModified(topic)
        val lifetime = timeUtils.subtract(date, timestamp)
        return lifetime >= TIMEOUT
    }

    private fun updateCache() {
        onStart()
        val args = NewsService.buildQuery(topic, date)
        loader.loadAsync(args)
    }

    companion object {
        /*
        * Timeout in milliseconds before cache would be invalidated.
        * */
        const val TIMEOUT = 60_000
    }
}