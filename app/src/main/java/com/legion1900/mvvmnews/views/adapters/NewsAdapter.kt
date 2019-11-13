package com.legion1900.mvvmnews.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.legion1900.mvvmnews.R
import com.legion1900.mvvmnews.models.data.Article

class NewsAdapter(private val context: Context, private val listener: View.OnClickListener) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private val dataSet = ArrayList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rv, parent, false)
        itemView.setOnClickListener(listener)
        return ArticleViewHolder(itemView)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = dataSet[position]
        holder.author.text = context.getString(R.string.author, article.author)
        holder.title.text = context.getString(R.string.title, article.title)
        holder.publishedAt.text = article.publishedAt
    }

    fun updateData(data: List<Article>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.findViewById(R.id.tv_author)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val publishedAt: TextView = itemView.findViewById(R.id.tv_publishedAt)
    }
}