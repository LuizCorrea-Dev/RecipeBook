package com.luizcorrea.recipebook.data.remote

import com.luizcorrea.recipebook.data.model.api.CategoriesResponse
import com.luizcorrea.recipebook.data.model.api.MealsResponse
import com.luizcorrea.recipebook.data.model.api.RecipeResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getCategories() : Response<CategoriesResponse>
    suspend fun getMealsByCategory(category: String) : Response<MealsResponse>
    suspend fun getRecipeDetails(idMeal: Int): Response<RecipeResponse>
}