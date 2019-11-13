package com.legion1900.mvvmnews.views

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.legion1900.mvvmnews.R
import com.legion1900.mvvmnews.models.data.entities.Article
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {

    private val picasso = Picasso.get()
    private lateinit var glide: RequestManager

    private lateinit var ivPicasso: ImageView
    private lateinit var ivGlide: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvSource: TextView
    private lateinit var tvDescription: TextView

    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        setSupportActionBar(toolbar)

        findViews()
        glide = Glide.with(this)
        receiveArticle()
        initImageViews()
        initTextViews()
    }

    private fun receiveArticle() {
        article = intent?.getParcelableExtra(KEY_ARTICLE)!!
    }

    private fun findViews() {
        tvTitle = findViewById(R.id.tv_title)
        tvSource = findViewById(R.id.tv_source_name)
        tvDescription = findViewById(R.id.tv_description)
        ivPicasso = findViewById(R.id.iv_picasso)
        ivGlide = findViewById(R.id.iv_glide)
    }

    private fun initImageViews() {
        article.urlToImage?.also {
            picasso.load(it)
                .placeholder(R.drawable.ic_image_grey_24dp)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(ivPicasso)
            glide.load(it)
                .placeholder(R.drawable.ic_image_grey_24dp)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(ivGlide)
        } ?: run{
            val group = findViewById<LinearLayout>(R.id.group_pictures)
            group.visibility = View.GONE
        }
    }

    private fun initTextViews() {
        tvTitle.text = getString(R.string.title, article.title)
        tvSource.text = getString(R.string.source, article.sourceName)
        tvDescription.text = article.description
    }

    companion object {
        const val KEY_ARTICLE = "article"
    }
}
