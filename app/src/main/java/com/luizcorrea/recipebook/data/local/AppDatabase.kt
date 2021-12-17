package com.luizcorrea.recipebook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luizcorrea.recipebook.data.local.dao.CategoryDao
import com.luizcorrea.recipebook.data.local.dao.MealDao
import com.luizcorrea.recipebook.data.model.db.Category
import com.luizcorrea.recipebook.data.model.db.Meal

@Database(entities = [Category::class, Meal::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao() : CategoryDao
    abstract fun mealsDao() : MealDao
}