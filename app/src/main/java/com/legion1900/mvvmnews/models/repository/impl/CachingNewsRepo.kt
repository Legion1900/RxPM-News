package com.legion1900.mvvmnews.models.repository.impl

import com.legion1900.mvvmnews.models.repository.impl.network.NewsService
import com.legion1900.mvvmnews.models.data.Response
import com.legion1900.mvvmnews.models.repository.abs.NewsRepository
import com.legion1900.mvvmnews.models.repository.impl.network.NewsLoader
import com.legion1900.mvvmnews.utils.TimeUtils
import java.util.*

class CachingNewsRepo(
    override val onStartCallback: () -> Unit,
    val provideNews: (Response) -> Unit,
    override val onFailureCallback: () -> Unit
) : NewsRepository {

    companion object {
        /*
        * Minimal timeout in milliseconds before reloading data for the same topic.
        * */
        const val TIMEOUT = 60_000
    }

    override val onLoadedCallback: (Response) -> Unit = { response ->
        this.response = response
        topic = requestTopic
        timestamp = requestDate
        provideNews(response)
    }

    private val timeUtils = TimeUtils()

    private val executor = NewsLoader(onLoadedCallback, onFailureCallback)

    private lateinit var requestTopic: String
    private lateinit var requestDate: Date

    private var topic: String? = null
    private var timestamp: Date? = null
    var response: Response? = null
        private set

    override fun loadNews(topic: String) {
        val date = TimeUtils.getCurrentDate()
        if (topic != this.topic || isOutdated(date)) {
            this.requestTopic = topic
            requestDate = date
            startLoading(topic, date)
        } else
            provideNews(response!!)
    }

    private fun isOutdated(newDate: Date): Boolean {
        return timestamp?.run {
            val delta = timeUtils.subtract(newDate, timestamp!!)
            delta >= TIMEOUT
        } ?: true
    }

    private fun startLoading(topic: String, date: Date) {
        onStartCallback()
        val query = NewsService.buildQuery(topic, date)
        executor.loadAsync(query)
    }
}