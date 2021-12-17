package com.luizcorrea.recipebook.ui.viewmodel

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface FilterByCategoryViewModelFactory {
    fun create(@Assisted("category") category: String?,
               @Assisted("isFavorites") isFavorites: String): FilterByCategoryViewModel
}