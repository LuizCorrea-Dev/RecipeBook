package com.luizcorrea.recipebook.data.model.db

import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "meals")
data class Meal(
        @PrimaryKey
        @Json(name = "idMeal")
        val id: Int,
        @Json(name = "strMeal")
        val mealName: String,
        @Json(name = "strMealThumb")
        val mealImageUrl: String,
        val category: String?,
        val isFavorite: Int = 0
)