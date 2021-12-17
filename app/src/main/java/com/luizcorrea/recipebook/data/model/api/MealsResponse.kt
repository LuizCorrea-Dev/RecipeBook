package com.luizcorrea.recipebook.data.model.api

import com.luizcorrea.recipebook.data.model.db.Meal
import com.squareup.moshi.Json

data class MealsResponse (@Json(name = "meals")val meals: List<Meal>)