package com.legion1900.mvvmnews.presenters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.repository.impl.NewsCache
import com.legion1900.mvvmnews.models.repository.impl.NewsRepo

class NewsPresentationModel(application: Application) : AndroidViewModel(application),
    PresentationModel {

    val news: LiveData<List<Article>>
    val isLoading: LiveData<Boolean>
    val isError: LiveData<Boolean>

    private val mNews = MutableLiveData<List<Article>>()
    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsError = MutableLiveData<Boolean>()

    private val cache = NewsCache(application)
    private val repo = NewsRepo(
        cache,
        onStartCallback = {
            mIsError.postValue(false)
            mIsLoading.postValue(true)
        },
        onLoadedCallback = {
            mIsLoading.postValue(false)
            mNews.postValue(it)
        },
        onFailureCallback = {
            mIsLoading.value = false
            mIsError.value = true
        }
    )

    init {
        news = mNews
        isLoading = mIsLoading
        isError = mIsError
    }

    override fun updateNewsfeed(topic: String) {
        repo.loadNews(topic)
    }

    override fun onCleared() {
        super.onCleared()
        repo.clearCache()
    }
}