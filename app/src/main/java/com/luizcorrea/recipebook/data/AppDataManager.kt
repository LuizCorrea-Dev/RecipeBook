package com.luizcorrea.recipebook.data

import com.luizcorrea.recipebook.data.local.DbHelper
import com.luizcorrea.recipebook.data.model.api.CategoriesResponse
import com.luizcorrea.recipebook.data.model.api.MealsResponse
import com.luizcorrea.recipebook.data.model.api.RecipeResponse
import com.luizcorrea.recipebook.data.remote.ApiHelper
import com.luizcorrea.recipebook.data.model.db.Meal
import retrofit2.Response
import javax.inject.Inject

class AppDataManager @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DbHelper
) : DataManager {
    override suspend fun getCategories(): Response<CategoriesResponse> {
        val cachedCategories = dbHelper.getCategories()
        if (!cachedCategories.isNullOrEmpty()) {
            return Response.success(CategoriesResponse(cachedCategories))
        }
        val apiResponse = apiHelper.getCategories()
        if (apiResponse.isSuccessful) {
            dbHelper.saveCategories((apiResponse.body() as CategoriesResponse).categories)
        } else {
            return Response.error(apiResponse.errorBody(), null)
        }
        return Response.success(CategoriesResponse(dbHelper.getCategories()))
    }

    override suspend fun getMealsByCategory(category: String): Response<MealsResponse> {
        val cachedMeals = dbHelper.getMealsByCategory(category)
        if (!cachedMeals.isNullOrEmpty()) {
            return Response.success(MealsResponse(cachedMeals))
        }

        val apiResponse = apiHelper.getMealsByCategory(category)
        if (apiResponse.isSuccessful) {
            var mealsData = (apiResponse.body() as MealsResponse).meals.toMutableList()
            dbHelper.saveMealsByCategory(mealsData, category)
        } else {
            return Response.error(apiResponse.errorBody(), null)
        }
        return Response.success(MealsResponse(dbHelper.getMealsByCategory(category)))
    }

    override suspend fun isFavorite(mealId: Int): Int {
        return dbHelper.isFavorite(mealId)
    }

    override suspend fun setFavorite(meal: Meal) {
        dbHelper.setFavorite(meal)
    }

    override suspend fun setFavorite(mealId: Int, isFavorite: Int) {
        dbHelper.setFavorite(mealId, isFavorite)
    }

    override suspend fun getFavoriteMeals(): Response<MealsResponse> {
        return Response.success(MealsResponse(dbHelper.getFavoriteMeals()))
    }

    override suspend fun getRecipeDetails(idMeal: Int): Response<RecipeResponse> {
        val apiResponse = apiHelper.getRecipeDetails(idMeal)
        if (apiResponse.isSuccessful) {
            var mealsData = (apiResponse.body() as RecipeResponse).meals.toMutableList()
            return Response.success(RecipeResponse(mealsData))
        } else {
            return Response.error(apiResponse.errorBody(), null)
        }
    }
}
