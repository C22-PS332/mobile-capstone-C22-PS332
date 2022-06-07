package com.c22ps322.capstone.modules.network

import com.c22ps322.capstone.models.spoonacular.SpoonacularResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpoonacularFoodService {

    @GET("recipes/{id}/information?includeNutrition=true")
    suspend fun getRecipeInformation(
        @Header("x-api-key") apiKey: String,
        @Path("id") id: Int
    ): Response<SpoonacularResponse>
}