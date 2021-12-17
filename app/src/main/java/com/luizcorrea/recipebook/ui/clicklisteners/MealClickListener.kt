package com.luizcorrea.recipebook.ui.clicklisteners
import com.luizcorrea.recipebook.data.model.db.Meal

interface MealClickListener {
    fun onMealClicked(meal: Meal)
}