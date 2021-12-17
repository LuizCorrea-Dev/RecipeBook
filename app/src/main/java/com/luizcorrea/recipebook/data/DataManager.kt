package com.luizcorrea.recipebook.data

import com.luizcorrea.recipebook.data.model.api.CategoriesResponse
import com.luizcorrea.recipebook.data.model.api.MealsResponse
import com.luizcorrea.recipebook.data.model.api.RecipeResponse
import com.luizcorrea.recipebook.data.model.db.Meal

import retrofit2.Response

interface DataManager {
    suspend fun getCategories() : Response<CategoriesResponse>
    suspend fun getMealsByCategory(category: String) : Response<MealsResponse>
    suspend fun isFavorite(mealId: Int) : Int
    suspend fun setFavorite(meal : Meal)
    suspend fun setFavorite(mealId: Int, isFavorite: Int)
    suspend fun getFavoriteMeals(): Response<MealsResponse>
    suspend fun getRecipeDetails(idMeal: Int): Response<RecipeResponse>
}