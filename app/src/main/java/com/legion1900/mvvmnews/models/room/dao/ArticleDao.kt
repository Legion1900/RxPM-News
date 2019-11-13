package com.legion1900.mvvmnews.models.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.legion1900.mvvmnews.models.room.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg articles: ArticleEntity)

    @Query("SELECT * FROM Article WHERE id IN (:ids)")
    fun getArticlesFor(ids: IntArray): List<ArticleEntity>

    @Query("DELETE FROM Article")
    fun clear()
}