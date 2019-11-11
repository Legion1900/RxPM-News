package com.legion1900.mvvmnews.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.legion1900.mvvmnews.R

import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        setSupportActionBar(toolbar)
    }

}
