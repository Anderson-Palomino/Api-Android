package com.example.t3_anderson_palomino.model

data class MealDetailResponse(
    val meals: List<MealDetail>
)

data class MealDetail(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strYoutube: String,
    val strMealThumb: String,
    val strInstructions: String?
)
