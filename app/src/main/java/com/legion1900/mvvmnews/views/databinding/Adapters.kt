package com.legion1900.mvvmnews.views.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.views.adapters.NewsAdapter

@BindingAdapter("app:data")
fun setRecyclerViewData(rv: RecyclerView, data: List<Article>?) {
    data?.let {
        (rv.adapter as? NewsAdapter)?.updateData(it)
    }
}