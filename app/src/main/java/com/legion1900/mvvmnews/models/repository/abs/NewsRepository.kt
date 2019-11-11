package com.legion1900.mvvmnews.models.repository.abs

import com.legion1900.mvvmnews.models.data.Response

interface NewsRepository {
    /*
    * Async callbacks.
    * */
    val onStartCallback: () -> Unit
    val onLoadedCallback: (Response) -> Unit
    val onFailureCallback: () -> Unit
    /*
    * Asks to update cached in 'news' property articles.
    * */
    fun loadNews(topic: String)
}