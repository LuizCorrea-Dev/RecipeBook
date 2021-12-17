package com.luizcorrea.recipebook.data.model.api

import com.squareup.moshi.Json

data class MealDetail(
    @Json(name = "idMeal") val idMeal: String,
    @Json(name = "strMeal") val mealName: String,
    @Json(name = "strCategory") val category: String,
    @Json(name = "strArea") val area: String,
    @Json(name = "strInstructions") val instructions: String,
    @Json(name = "strMealThumb") val mealImg: String,
    @Json(name = "strTags") val tags: String?,
    @Json(name = "strYoutube") val youtubeLink: String,
    @Json(name = "strIngredient1") val strIngredient1: String?,
    @Json(name = "strIngredient2") val strIngredient2: String?,
    @Json(name = "strIngredient3") val strIngredient3: String?,
    @Json(name = "strIngredient4") val strIngredient4: String?,
    @Json(name = "strIngredient5") val strIngredient5: String?,
    @Json(name = "strIngredient6") val strIngredient6: String?,
    @Json(name = "strIngredient7") val strIngredient7: String?,
    @Json(name = "strIngredient8") val strIngredient8: String?,
    @Json(name = "strIngredient9") val strIngredient9: String?,
    @Json(name = "strIngredient10") val strIngredient10: String?,
    @Json(name = "strIngredient11") val strIngredient11: String?,
    @Json(name = "strIngredient12") val strIngredient12: String?,
    @Json(name = "strIngredient13") val strIngredient13: String?,
    @Json(name = "strIngredient14") val strIngredient14: String?,
    @Json(name = "strIngredient15") val strIngredient15: String?,
    @Json(name = "strIngredient16") val strIngredient16: String?,
    @Json(name = "strIngredient17") val strIngredient17: String?,
    @Json(name = "strIngredient18") val strIngredient18: String?,
    @Json(name = "strIngredient19") val strIngredient19: String?,
    @Json(name = "strIngredient20") val strIngredient20: String?,

    @Json(name = "strMeasure1") val strMeasure1: String?,
    @Json(name = "strMeasure2") val strMeasure2: String?,
    @Json(name = "strMeasure3") val strMeasure3: String?,
    @Json(name = "strMeasure4") val strMeasure4: String?,
    @Json(name = "strMeasure5") val strMeasure5: String?,
    @Json(name = "strMeasure6") val strMeasure6: String?,
    @Json(name = "strMeasure7") val strMeasure7: String?,
    @Json(name = "strMeasure8") val strMeasure8: String?,
    @Json(name = "strMeasure9") val strMeasure9: String?,
    @Json(name = "strMeasure10") val strMeasure10: String?,
    @Json(name = "strMeasure11") val strMeasure11: String?,
    @Json(name = "strMeasure12") val strMeasure12: String?,
    @Json(name = "strMeasure13") val strMeasure13: String?,
    @Json(name = "strMeasure14") val strMeasure14: String?,
    @Json(name = "strMeasure15") val strMeasure15: String?,
    @Json(name = "strMeasure16") val strMeasure16: String?,
    @Json(name = "strMeasure17") val strMeasure17: String?,
    @Json(name = "strMeasure18") val strMeasure18: String?,
    @Json(name = "strMeasure19") val strMeasure19: String?,
    @Json(name = "strMeasure20") val strMeasure20: String?
) {
    fun getIngredients(): String {
        var ingredients: String = ""
        if (!strIngredient1.isNullOrEmpty()  && !strMeasure1.isNullOrEmpty())
            ingredients += strIngredient1 + " : " + strMeasure1 + "\n"
        if (!strIngredient2.isNullOrEmpty() && !strMeasure2.isNullOrEmpty())
            ingredients += strIngredient2 + " : " + strMeasure2 + "\n"
        if (!strIngredient3.isNullOrEmpty() && !strMeasure3.isNullOrEmpty())
            ingredients += strIngredient3 + " : " + strMeasure3 + "\n"
        if (!strIngredient4.isNullOrEmpty() && !strMeasure4.isNullOrEmpty())
            ingredients += strIngredient4 + " : " + strMeasure4 + "\n"
        if (!strIngredient5.isNullOrEmpty() && !strMeasure5.isNullOrEmpty())
            ingredients += strIngredient5 + " : " + strMeasure5 + "\n"
        if (!strIngredient6.isNullOrEmpty() && !strMeasure6.isNullOrEmpty())
            ingredients += strIngredient6 + " : " + strMeasure6 + "\n"
        if (!strIngredient7.isNullOrEmpty() && !strMeasure7.isNullOrEmpty())
            ingredients += strIngredient7 + " : " + strMeasure7 + "\n"
        if (!strIngredient8.isNullOrEmpty() && !strMeasure8.isNullOrEmpty())
            ingredients += strIngredient8 + " : " + strMeasure8 + "\n"
        if (!strIngredient9.isNullOrEmpty() && !strMeasure9.isNullOrEmpty())
            ingredients += strIngredient9 + " : " + strMeasure9 + "\n"
        if (!strIngredient10.isNullOrEmpty()  && !strMeasure10.isNullOrEmpty())
            ingredients += strIngredient10 + " : " + strMeasure10 + "\n"
        if (!strIngredient11.isNullOrEmpty()  && !strMeasure11.isNullOrEmpty())
            ingredients += strIngredient11 + " : " + strMeasure11 + "\n"
        if (!strIngredient12.isNullOrEmpty()  && !strMeasure12.isNullOrEmpty())
            ingredients += strIngredient12 + " : " + strMeasure12 + "\n"
        if (!strIngredient13.isNullOrEmpty()  && !strMeasure13.isNullOrEmpty())
            ingredients += strIngredient13 + " : " + strMeasure13 + "\n"
        if (!strIngredient14.isNullOrEmpty()  && !strMeasure14.isNullOrEmpty())
            ingredients += strIngredient14 + " : " + strMeasure14 + "\n"
        if (!strIngredient15.isNullOrEmpty()  && !strMeasure15.isNullOrEmpty())
            ingredients += strIngredient15 + " : " + strMeasure15 + "\n"
        if (!strIngredient16.isNullOrEmpty()  && !strMeasure16.isNullOrEmpty())
            ingredients += strIngredient16 + " : " + strMeasure16 + "\n"
        if (!strIngredient17.isNullOrEmpty()  && !strMeasure17.isNullOrEmpty())
            ingredients += strIngredient17 + " : " + strMeasure17 + "\n"
        if (!strIngredient18.isNullOrEmpty()  && !strMeasure18.isNullOrEmpty())
            ingredients += strIngredient18 + " : " + strMeasure18 + "\n"
        if (!strIngredient19.isNullOrEmpty()  && !strMeasure19.isNullOrEmpty())
            ingredients += strIngredient19 + " : " + strMeasure19 + "\n"
        if (!strIngredient20.isNullOrEmpty()  && !strMeasure20.isNullOrEmpty())
            ingredients += strIngredient20 + " : " + strMeasure20 + "\n"
        return ingredients
    }
}