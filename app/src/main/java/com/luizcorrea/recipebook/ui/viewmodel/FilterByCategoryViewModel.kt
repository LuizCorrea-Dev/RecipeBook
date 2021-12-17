package com.luizcorrea.recipebook.ui.viewmodel

import androidx.lifecycle.*
import com.luizcorrea.recipebook.utils.*
import com.luizcorrea.recipebook.utils.CATEGORY
import com.luizcorrea.recipebook.utils.IS_FAVORITES
import com.luizcorrea.recipebook.data.AppDataManager
import com.luizcorrea.recipebook.data.model.api.MealsResponse
import com.luizcorrea.recipebook.data.model.db.Meal
import com.luizcorrea.recipebook.utils.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.databinding.DataBindingUtil
import androidx.hilt.lifecycle.ViewModelInject
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*


class FilterByCategoryViewModel @AssistedInject constructor(
    val dataManager: AppDataManager,
    val networkHelper: NetworkHelper,
    @Assisted("category") private val category: String?,
    @Assisted("isFavorites") private val isFavorites: String
) : ViewModel() {

    class Factory(
        private val assistedFactory: FilterByCategoryViewModelFactory,
        private val category: String?,
        private val isFavorites: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(category, isFavorites) as T
        }

    }

    private val _searchText = MutableLiveData<String>()
    val searchText = _searchText

    private val _meals = MutableLiveData<Resource<MealsResponse>>()

    val meals: LiveData<Resource<MealsResponse>>
        get() = _meals

    init {
        if (isFavorites.equals("Y")) fetchFavorites()
        else fetchMealsByCategory(category)
    }

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    fun fetchMealsByCategory(category: String?) {
        viewModelScope.launch {
            _meals.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                launch(Dispatchers.IO) {
                    category?.let { value ->
                        dataManager.getMealsByCategory(value).let {
                            if (it.isSuccessful) {
                                _meals.postValue(Resource.success(it.body()))
                            } else _meals.postValue(
                                Resource.error(
                                    it.errorBody().toString(),
                                    null
                                )
                            )
                        }
                    }
                }
            } else _meals.postValue(Resource.error("No Internet Connection", null))
        }
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            _meals.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                launch(Dispatchers.IO) {
                    dataManager.getFavoriteMeals().let {
                        if (it.isSuccessful) {
                            _meals.postValue(Resource.success(it.body()))
                        } else _meals.postValue(
                            Resource.error(
                                it.errorBody().toString(),
                                null
                            )
                        )
                    }
                }
            } else _meals.postValue(Resource.error("No Internet Connection", null))
        }
    }

    fun onFavoriteClicked(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = dataManager.isFavorite(meal.id)
            val _meal = meal.copy(
                isFavorite = when (isFavorite) {
                    1 -> 0
                    else -> 1
                }
            )
            dataManager.setFavorite(_meal)
            if (isFavorites.equals("Y"))
                fetchFavorites()
            else
                fetchMealsByCategory(category)
        }
    }
}