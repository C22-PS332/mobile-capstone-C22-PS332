package com.c22ps322.mealsnap.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    val status: String?
)
