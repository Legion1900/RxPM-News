package com.legion1900.mvvmnews.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.legion1900.mvvmnews.models.data.entities.Article

@Dao
interface ArticleDao {
    @Insert
    fun insert(vararg articles: Article)

    @Query("DELETE FROM Article WHERE id IN (:ids)")
    fun deleteArticles(ids: IntArray)

    @Query("SELECT * FROM Article WHERE id IN (:ids)")
    fun getArticlesFor(ids: IntArray): List<Article>

    @Query("DELETE FROM Article")
    fun clear()
}