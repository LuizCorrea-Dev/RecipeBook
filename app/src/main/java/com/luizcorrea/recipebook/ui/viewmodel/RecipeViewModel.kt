package com.luizcorrea.recipebook.ui.viewmodel

import androidx.lifecycle.*
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luizcorrea.recipebook.utils.Resource
import com.luizcorrea.recipebook.data.AppDataManager
import com.luizcorrea.recipebook.data.model.api.RecipeResponse
import com.luizcorrea.recipebook.data.model.db.Meal
import com.luizcorrea.recipebook.utils.NetworkHelper
import com.luizcorrea.recipebook.utils.SingleLiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeViewModel @AssistedInject constructor(
    val dataManager: AppDataManager,
    val networkHelper: NetworkHelper,
    @Assisted private val mealId: Int?
) : ViewModel() {

    class Factory(
        private val assistedFactory: RecipeViewModelFactory,
        private val mealId: Int?
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(mealId) as T
        }
    }

    private val _recipe = MutableLiveData<Resource<RecipeResponse>>()
    val recipe: LiveData<Resource<RecipeResponse>> get() = _recipe

    private val _favorite = MutableLiveData<Int>()
    val favorite: LiveData<Int> get() = _favorite

    init {
        fetchRecipe()
    }

    private fun fetchRecipe() {
        viewModelScope.launch {
            _recipe.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                launch(Dispatchers.IO) {
                    mealId?.let { value ->
                        dataManager.getRecipeDetails(value).let {
                            if (it.isSuccessful) {
                                _recipe.postValue(Resource.success(it.body()))
                            } else _recipe.postValue(
                                Resource.error(
                                    it.errorBody().toString(),
                                    null
                                )
                            )
                        }
                    }
                }
            } else _recipe.postValue(Resource.error("No Internet Connection", null))
        }
    }

    fun isFavorite(mealId: Int) {
        viewModelScope.launch {
            _favorite.postValue(0)
            launch(Dispatchers.IO) {
                _favorite.postValue(dataManager.isFavorite(mealId))
            }
        }
    }

    fun onFavoriteClicked(mealId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = dataManager.isFavorite(mealId)
            val favoriteVal = when (isFavorite) {
                1 -> 0
                else -> 1
            }
            dataManager.setFavorite(mealId, favoriteVal)
            isFavorite(mealId)
        }
    }

}