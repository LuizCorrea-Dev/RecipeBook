package com.luizcorrea.recipebook.data.remote

import com.luizcorrea.recipebook.data.model.api.CategoriesResponse
import com.luizcorrea.recipebook.data.model.api.MealsResponse
import com.luizcorrea.recipebook.data.model.api.RecipeResponse
import retrofit2.Response
import javax.inject.Inject

class AppApiHelper @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getCategories(): Response<CategoriesResponse> = apiService.getCategories()
    override suspend fun getMealsByCategory(category: String): Response<MealsResponse> {
        return apiService.getMealsByCategory(category)
    }

    override suspend fun getRecipeDetails(idMeal: Int): Response<RecipeResponse> {
        return apiService.getRecipeDetails(idMeal)
    }
}