package com.legion1900.mvvmnews.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.legion1900.mvvmnews.R

import kotlinx.android.synthetic.main.activity_newsfeed.*

class NewsfeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsfeed)
        setSupportActionBar(toolbar)
    }
}
