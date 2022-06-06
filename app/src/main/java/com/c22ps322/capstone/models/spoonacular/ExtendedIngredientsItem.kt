package com.c22ps322.capstone.models.spoonacular

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredientsItem(

	@Json(name="image")
	val image: String,

	@Json(name="original")
	val original: String,

	@Json(name="name")
	val name: String,

	@Json(name="id")
	val id: Int
): Parcelable