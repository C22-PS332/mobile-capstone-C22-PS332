package com.c22ps322.capstone.models.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(

    @Json(name = "access_token")
    val token: String
)