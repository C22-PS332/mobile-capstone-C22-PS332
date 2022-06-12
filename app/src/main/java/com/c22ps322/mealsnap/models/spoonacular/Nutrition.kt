package com.c22ps322.mealsnap.models.spoonacular

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nutrition(
    @Json(name = "weightPerServing")
    val weightPerServing: WeightPerServing?,

    @Json(name = "nutrients")
    val nutrients: List<NutrientsItem>?
) : Parcelable