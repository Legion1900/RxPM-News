package com.legion1900.mvvmnews.views

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.legion1900.mvvmnews.BuildConfig
import com.legion1900.mvvmnews.R
import com.legion1900.mvvmnews.databinding.ActivityNewsfeedBinding
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.presenters.NewsPresentationModel
import com.legion1900.mvvmnews.utils.dialogs.ErrorDialog
import com.legion1900.mvvmnews.views.adapters.NewsAdapter
import com.legion1900.mvvmnews.views.listeners.OnTopicSelectedListener
import kotlinx.android.synthetic.main.activity_newsfeed.*


class NewsfeedActivity : AppCompatActivity() {

    private lateinit var model: NewsPresentationModel

    private lateinit var binding: ActivityNewsfeedBinding

    private val adapter =
        NewsAdapter(this, View.OnClickListener {
            val i = binding.rvNews.getChildAdapterPosition(it)
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_newsfeed)
        binding.lifecycleOwner = this
        setSupportActionBar(toolbar)

        initModel()
        binding.viewModel = model
        initRecyclerView()
        initSpinner()
        initSwipeRefresh()
        updateContent()
    }

    private fun initModel() {
        model = ViewModelProviders.of(this)[NewsPresentationModel::class.java]
        model.isLoading.observe(
            this,
            Observer<Boolean> { binding.swipeRefresh.isRefreshing = it }
        )
        model.isError.observe(this, Observer<Boolean> {
            if (it)
                errDialog.show(supportFragmentManager, BuildConfig.APPLICATION_ID)
            else if (errDialog.isDetached)
                errDialog.dismiss()
        })
    }

    private fun initRecyclerView() {
        binding.rvNews.adapter = adapter
    }

    private fun initSpinner() {
        // False trigger prevention.
        binding.spinnerTopics.run {
            setSelection(0, false)
            onItemSelectedListener = OnTopicSelectedListener { updateContent() }
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener { updateContent() }
    }

    /*
    * Asks model to load news.
    * */
    private fun updateContent() {
        val topic = binding.spinnerTopics.selectedItem as String
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
