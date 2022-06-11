package com.c22ps322.capstone.models.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Recipe(
    val id: Int,

    val title: String,

    val summary: String,

    @Json(name = "image")
    val imageUrl: String,
): Parcelable