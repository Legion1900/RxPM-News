package com.legion1900.mvvmnews.models.repository.impl.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AsyncExecutor(
    onResponse: (com.legion1900.mvvmnews.models.data.Response?) -> Unit,
    onFailure: () -> Unit
) {

    companion object QueryBuilder {
        private const val BASE_URL = "https://newsapi.org"
    }

    private val service: NewsService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(NewsService::class.java)
    }

    private val callback = object : Callback<com.legion1900.mvvmnews.models.data.Response> {
        override fun onFailure(
            call: Call<com.legion1900.mvvmnews.models.data.Response>,
            t: Throwable
        ) {
            onFailure()
        }

        override fun onResponse(
            call: Call<com.legion1900.mvvmnews.models.data.Response>,
            response: Response<com.legion1900.mvvmnews.models.data.Response>
        ) {
            onResponse(response.body())
        }
    }

    fun execAsync(queryArgs: Map<String, String>) {
        val call = service.queryNews(queryArgs)
        call.enqueue(callback)
    }
}