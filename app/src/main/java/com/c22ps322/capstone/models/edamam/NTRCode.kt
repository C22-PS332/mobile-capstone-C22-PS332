package com.c22ps322.capstone.models.edamam

import com.squareup.moshi.Json

data class NTRCode(
    @Json(name="label")
    val label: String,

    @Json(name="quantity")
    val quantity: Double,

    @Json(name="unit")
    val unit: String,
)
