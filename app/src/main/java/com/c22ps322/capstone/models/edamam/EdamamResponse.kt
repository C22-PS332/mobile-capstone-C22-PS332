package com.c22ps322.capstone.models.edamam

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EdamamResponse(
	@Json(name="totalWeight")
	val totalWeight: Double? = null,

	@Json(name="totalDaily")
	val totalDaily: TotalDaily? = null,

	@Json(name="calories")
	val calories: Int? = null,
)


