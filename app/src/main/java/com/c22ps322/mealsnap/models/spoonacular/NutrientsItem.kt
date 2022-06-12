package com.c22ps322.mealsnap.models.spoonacular

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class NutrientsItem(

    @Json(name = "amount")
    val amount: Double?,

    @Json(name = "unit")
    val unit: String?,

    @Json(name = "percentOfDailyNeeds")
    val percentOfDailyNeeds: Double?,

    @Json(name = "name")
    val name: String?
) : Parcelable