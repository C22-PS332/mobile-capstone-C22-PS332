package com.c22ps322.capstone.models.spoonacular

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SpoonacularResponse(

	@Json(name="readyInMinutes")
	val readyInMinutes: Int,

	@Json(name="summary")
	val summary: String,

	@Json(name="image")
	val image: String,

	@Json(name="servings")
	val servings: Int,

	@Json(name="nutrition")
	val nutrition: Nutrition,

	@Json(name="id")
	val id: Int,

	@Json(name="title")
	val title: String,

	@Json(name = "creditsText")
	val creditsText: String,

	@Json(name = "sourceUrl")
	val sourceUrl: String,

	@Json(name="extendedIngredients")
	val extendedIngredients: List<ExtendedIngredientsItem>
): Parcelable