package com.luizcorrea.recipebook.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    @Json(name = "idCategory")
    val id: String,
    @Json(name = "strCategory")
    val categoryName: String,
    @Json(name = "strCategoryThumb")
    val thumbnail: String,
    @Json(name = "strCategoryDescription")
    val description: String
)