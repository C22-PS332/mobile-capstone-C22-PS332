package com.c22ps322.capstone.models.spoonacular

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Parcelize
data class CaloricBreakdown(

	@Json(name="percentCarbs")
	val percentCarbs: Double,

	@Json(name="percentProtein")
	val percentProtein: Double,

	@Json(name="percentFat")
	val percentFat: Double
): Parcelable