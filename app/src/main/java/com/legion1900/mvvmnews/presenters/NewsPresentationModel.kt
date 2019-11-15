package com.legion1900.mvvmnews.presenters

import android.app.Application
import androidx.lifecycle.*
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.repository.impl.CacheRepo
import com.legion1900.mvvmnews.models.repository.impl.CacheValidator

class NewsPresentationModel(application: Application) : AndroidViewModel(application),
    PresentationModel {
    override fun articleOnPosition(i: Int) {
        return articles.value!![i]
    }

    private lateinit var articleObserver: Observer<List<Article>>
    private lateinit var lifecycleOwner: LifecycleOwner
    private var topic: String? = null

    private var articles: LiveData<List<Article>> = MutableLiveData()
    private val isLoading = MutableLiveData<Boolean>()
    private val isError = MutableLiveData<Boolean>()

    private val cache = CacheRepo(application)
    private val cacheValidator = CacheValidator(
        cache,
        onStart = {
            isError.value = false
            isLoading.value = true
        },
        onFinished = {
            isLoading.value = false
        },
        onFailure = {
            isLoading.value = false
            isError.value = true
        }
    )

    override fun observeIsLoading(owner: LifecycleOwner, observer: Observer<Boolean>) {
        isLoading.observe(owner, observer)
    }

    override fun observeIsError(owner: LifecycleOwner, observer: Observer<Boolean>) {
        isError.observe(owner, observer)
    }

    override fun observeArticles(owner: LifecycleOwner, observer: Observer<List<Article>>) {
        articleObserver = observer
        lifecycleOwner = owner
        articles.observe(owner, observer)
    }

    override fun updateNewsfeed(topic: String) {
        cacheValidator.updateNewsFor(topic)
        if (this.topic != topic) {
            articles = cache.readArticles(topic)
            articles.observe(lifecycleOwner, articleObserver)
        }
    }
}