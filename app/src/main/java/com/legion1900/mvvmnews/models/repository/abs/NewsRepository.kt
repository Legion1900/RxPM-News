package com.legion1900.mvvmnews.models.repository.abs

import com.legion1900.mvvmnews.models.data.Article

interface NewsRepository {
    val onStartCallback: () -> Unit
    val onLoadedCallback: (List<Article>) -> Unit
    val onFailureCallback: () -> Unit
    /*
    * Asks to update cached in 'news' property articles.
    * */
    fun loadNews(topic: String)
}