package com.c22ps322.capstone.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    val status: String
)
