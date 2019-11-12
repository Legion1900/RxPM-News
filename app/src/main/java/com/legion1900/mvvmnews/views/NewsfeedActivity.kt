package com.legion1900.mvvmnews.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.legion1900.mvvmnews.R
import com.legion1900.mvvmnews.views.adapters.NewsAdapter

import kotlinx.android.synthetic.main.activity_newsfeed.*

class NewsfeedActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private val adapter = NewsAdapter(this, View.OnClickListener {
//        TODO: add code for opening activity
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsfeed)
        setSupportActionBar(toolbar)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv = findViewById(R.id.rv_news)
        rv.adapter = adapter
    }
}
