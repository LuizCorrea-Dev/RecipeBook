package com.luizcorrea.recipebook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luizcorrea.recipebook.data.model.db.Category
import io.reactivex.Single

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(options: List<Category>)

    @Query("SELECT * FROM categories")
    fun loadAll(): List<Category>
}