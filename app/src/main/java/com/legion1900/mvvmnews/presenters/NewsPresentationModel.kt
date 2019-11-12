package com.legion1900.mvvmnews.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.repository.impl.CachingNewsRepo

class NewsPresentationModel : ViewModel(),
    PresentationModel {

    val news: LiveData<List<Article>>
    val isLoading: LiveData<Boolean>
    val isError: LiveData<Boolean>

    private val mNews = MutableLiveData<List<Article>>()
    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsError = MutableLiveData<Boolean>()

    private val repo = CachingNewsRepo(
        onStartCallback = {
            mIsError.value = false
            mIsLoading.value = true
        },
        provideNews = {
            mIsLoading.value = false
            // TODO: temporary solution, implement DB observing
            mNews.value = it.articles
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
}