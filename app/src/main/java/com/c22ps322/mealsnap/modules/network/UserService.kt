package com.c22ps322.mealsnap.modules.network

import com.c22ps322.mealsnap.models.domain.ChangePasswordResponse
import com.c22ps322.mealsnap.models.domain.LoginResponse
import com.c22ps322.mealsnap.models.domain.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @POST("user")
    @Headers("Content-Type: application/json")
    suspend fun register(
        // name
        // email
        // password
        @Body param: RequestBody
    ): Response<RegisterResponse>

    @PUT("user/change-password")
    @Headers("Content-Type: application/json")
    suspend fun changePassword(
        // email
        // old password
        // new password
        @Body param: RequestBody
    ): Response<ChangePasswordResponse>
}