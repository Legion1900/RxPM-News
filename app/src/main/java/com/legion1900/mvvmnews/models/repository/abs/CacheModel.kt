package com.legion1900.mvvmnews.models.repository.abs

import androidx.lifecycle.LiveData
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.data.Response
import com.legion1900.mvvmnews.models.repository.impl.CacheRepo

interface CacheModel {
    val cacheRepo: CacheRepo
    fun updateNewsFor(topic: String)
}