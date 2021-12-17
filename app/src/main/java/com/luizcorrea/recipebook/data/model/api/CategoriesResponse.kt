package com.luizcorrea.recipebook.data.model.api

import com.luizcorrea.recipebook.data.model.db.Category
import com.squareup.moshi.Json

data class CategoriesResponse (@Json(name = "categories")val categories: List<Category>)