package com.luizcorrea.recipebook.data.remote

import com.luizcorrea.recipebook.data.model.api.CategoriesResponse
import com.luizcorrea.recipebook.data.model.api.MealsResponse
import com.luizcorrea.recipebook.data.model.api.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealsResponse>

    @GET("lookup.php")
    suspend fun getRecipeDetails(@Query("i") mealId: Int): Response<RecipeResponse>
}