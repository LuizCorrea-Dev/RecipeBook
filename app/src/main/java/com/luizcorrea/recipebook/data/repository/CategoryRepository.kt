package com.luizcorrea.recipebook.data.repository

import com.luizcorrea.recipebook.data.remote.ApiHelper
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getCategories() = apiHelper.getCategories()
}