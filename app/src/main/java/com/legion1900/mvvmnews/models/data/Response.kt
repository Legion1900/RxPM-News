package com.legion1900.mvvmnews.models.data

data class Response(
    val status: String,
    val code: String,
    val message: String,
    val articles: List<Article>
)