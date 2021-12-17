package com.luizcorrea.recipebook.ui.viewmodel

import dagger.assisted.AssistedFactory

@AssistedFactory
interface RecipeViewModelFactory {
    fun create(mealId: Int?): RecipeViewModel
}