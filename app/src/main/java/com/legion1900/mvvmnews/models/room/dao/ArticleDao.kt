package com.legion1900.mvvmnews.models.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.legion1900.mvvmnews.models.data.Article
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Insert
    fun insert(vararg articles: ArticleEntity)

    @Query("SELECT article FROM Article")
    fun getAllArticles(): List<Article>

    @Query("SELECT article FROM Article WHERE topic IN (:topic)")
    fun getArticlesFor(topic: String): List<Article>

    @Query("DELETE FROM Article")
    fun clear()
}