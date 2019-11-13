package com.legion1900.mvvmnews.views

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
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
import com.legion1900.mvvmnews.models.data.entities.Article
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
        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(ArticleActivity.KEY_ARTICLE, model.news.value?.get(i))
        startActivityTransition(intent, it)
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

    private fun startActivityTransition(intent: Intent, fromView: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                fromView,
                fromView.transitionName
            ).toBundle()
            startActivity(intent, options)
        } else
            startActivity(intent)
    }
}
