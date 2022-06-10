package com.c22ps322.capstone.models.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangePasswordResponse(
    val message: String?,

    val detail: String?
)