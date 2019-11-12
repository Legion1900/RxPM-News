package com.legion1900.mvvmnews.presenters

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.repository.impl.CachingNewsRepo
import com.legion1900.mvvmnews.views.ArticleActivity

class NewsPresentationModel(application: Application) : AndroidViewModel(application),
    PresentationModel {

    val news: LiveData<List<Article>>
    val isLoading: LiveData<Boolean>
    val isError: LiveData<Boolean>

    private val mNews = MutableLiveData<List<Article>>()
    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsError = MutableLiveData<Boolean>()

    private val repo = CachingNewsRepo(
        onStartCallback = { mIsLoading.value = true },
        onLoaded = {
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

    override fun onArticleClick(articleInd: Int) {
        val app = getApplication<Application>()
        val intent = Intent(app, ARTICLE_ACTIVITY)
        app.startActivity(intent)
    }

    private companion object {
        val ARTICLE_ACTIVITY = ArticleActivity::class.java
    }
}