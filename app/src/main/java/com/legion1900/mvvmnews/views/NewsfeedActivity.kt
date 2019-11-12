package com.legion1900.mvvmnews.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.legion1900.mvvmnews.BuildConfig
import com.legion1900.mvvmnews.R
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.presenters.NewsPresentationModel
import com.legion1900.mvvmnews.utils.dialogs.ErrorDialog
import com.legion1900.mvvmnews.views.adapters.NewsAdapter
import com.legion1900.mvvmnews.views.handlers.OnTopicSelectedListener
import kotlinx.android.synthetic.main.activity_newsfeed.*

class NewsfeedActivity : AppCompatActivity() {

    private lateinit var model: NewsPresentationModel

    private lateinit var spinner: Spinner
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private val adapter = NewsAdapter(this, View.OnClickListener {
        val i = rv.getChildAdapterPosition(it)
        model.onArticleClick(i)
        val intent = Intent(this, ArticleActivity::class.java)
        startActivity(intent)
    })

    private val errDialog: DialogFragment =
        ErrorDialog.createErrorDialog(
            R.string.msg_err,
            R.string.btn_positive,
            object : ErrorDialog.PositiveCallback {
                override fun onPositive() {
                    updateContent()
                }
            })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsfeed)
        setSupportActionBar(toolbar)

        swipeRefresh = findViewById(R.id.swipe_refresh)
        spinner = findViewById(R.id.spinner_topics)
        rv = findViewById(R.id.rv_news)
        initModel()
        initRecyclerView()
        initSpinner()
        initSwipeRefresh()
        updateContent()
    }

    private fun initModel() {
        model = ViewModelProviders.of(this)[NewsPresentationModel::class.java]
        model.news.observe(this, Observer<List<Article>> { adapter.updateData(it) })
        model.isLoading.observe(this, Observer<Boolean> { swipeRefresh.isRefreshing = it })
        model.isError.observe(this, Observer<Boolean> {
            if (it)
                errDialog.show(supportFragmentManager, BuildConfig.APPLICATION_ID)
            else if (errDialog.isDetached)
                errDialog.dismiss()
        })
    }

    private fun initRecyclerView() {
        rv = findViewById(R.id.rv_news)
        rv.adapter = adapter
    }

    private fun initSpinner() {
        spinner = findViewById(R.id.spinner_topics)
        // False trigger prevention.
        spinner.setSelection(0, false)
        spinner.onItemSelectedListener = OnTopicSelectedListener { updateContent() }
    }

    private fun initSwipeRefresh() {
        swipeRefresh = findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener { updateContent() }
    }

    /*
    * Asks model to load news.
    * */
    private fun updateContent() {
        val topic = spinner.selectedItem as String
        model.updateNewsfeed(topic)
    }
}
