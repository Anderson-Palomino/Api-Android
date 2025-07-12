package com.example.t3_anderson_palomino.api

import com.example.t3_anderson_palomino.model.MealDetailResponse
import com.example.t3_anderson_palomino.model.MealListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // Para obtener comidas por categoría
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealListResponse>

    // Para obtener el detalle de una comida por su ID
    @GET("lookup.php")
    suspend fun getMealById(@Query("i") idMeal: String): Response<MealDetailResponse>
}
