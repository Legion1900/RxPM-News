package com.legion1900.mvvmnews.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.legion1900.mvvmnews.models.data.Article

interface PresentationModel {
    fun observeIsLoading(owner: LifecycleOwner, observer: Observer<Boolean>)
    fun observeIsError(owner: LifecycleOwner, observer: Observer<Boolean>)
    fun observeArticles(owner: LifecycleOwner, observer: Observer<List<Article>>)
    fun updateNewsfeed(topic: String)
    fun articleOnPosition(i: Int): Article
}