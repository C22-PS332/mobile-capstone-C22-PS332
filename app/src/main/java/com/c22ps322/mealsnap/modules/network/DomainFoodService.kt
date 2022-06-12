package com.c22ps322.mealsnap.modules.network

import com.c22ps322.mealsnap.models.domain.Recipe
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DomainFoodService {

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<List<Recipe>>
}