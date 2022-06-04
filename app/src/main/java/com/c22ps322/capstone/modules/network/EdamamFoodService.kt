package com.c22ps322.capstone.modules.network

import com.c22ps322.capstone.models.edamam.EdamamResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface EdamamFoodService {

    @POST("nutrition-details")
    suspend fun getNutrition(
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Body param: RequestBody
    ): Response<EdamamResponse>
}