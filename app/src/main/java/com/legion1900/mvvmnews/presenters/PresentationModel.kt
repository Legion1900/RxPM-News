package com.legion1900.mvvmnews.presenters

import androidx.lifecycle.Observer
import com.legion1900.mvvmnews.models.data.Article

interface PresentationModel {
    var isLoadingObserver: Observer<Boolean>
    var isEerrorObserver: Observer<Boolean>
    var articleObserver: Observer<List<Article>>
    fun updateNewsfeed(topic: String)
}