package com.legion1900.mvvmnews.models.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.legion1900.mvvmnews.models.data.Article
import retrofit2.http.DELETE

@Dao
interface ArticleDao {
    @Insert
    fun insert(vararg articles: Article)

    @Query("DELETE FROM Article")
    fun clear()

    @Query("SELECT * FROM Article")
    fun getAll(): List<Article>
}