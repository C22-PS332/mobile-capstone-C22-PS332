package com.c22ps322.capstone.modules.network

import com.c22ps322.capstone.models.domain.Recipe
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DomainFoodService {

    @Multipart
    @POST("images")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): Response<List<Recipe>>
}