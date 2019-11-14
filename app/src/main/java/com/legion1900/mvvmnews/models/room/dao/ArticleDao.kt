package com.legion1900.mvvmnews.models.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Insert
    fun insert(vararg articles: ArticleEntity)

    @Query("SELECT * FROM Article")
    fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT * FROM Article WHERE topic IN (:topic)")
    fun getArticlesFor(topic: String): List<ArticleEntity>

    @Query("DELETE FROM Article")
    fun clear()
}