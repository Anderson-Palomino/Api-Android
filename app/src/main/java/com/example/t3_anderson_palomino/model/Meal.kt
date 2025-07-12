package com.example.t3_anderson_palomino.model

data class MealListResponse(
    val meals: List<Meal>
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)
