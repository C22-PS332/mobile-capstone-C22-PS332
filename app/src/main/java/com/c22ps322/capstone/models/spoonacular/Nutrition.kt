package com.c22ps322.capstone.models.spoonacular

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nutrition(
//
//	@Json(name="caloricBreakdown")
//	val caloricBreakdown: CaloricBreakdown,

	@Json(name="weightPerServing")
	val weightPerServing: WeightPerServing,

	@Json(name="nutrients")
	val nutrients: List<NutrientsItem>
): Parcelable