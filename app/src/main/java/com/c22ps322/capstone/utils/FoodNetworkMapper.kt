package com.c22ps322.capstone.utils

import com.c22ps322.capstone.models.edamam.EdamamRequestParam
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class FoodNetworkMapper @Inject constructor() : NetworkMapperInterface<EdamamRequestParam> {
    override fun toJson(target: EdamamRequestParam): RequestBody {
        val jsonObject = JSONObject()

        with(jsonObject) {
            put("title", target.title)

            put("ingr", JSONArray(target.ingredients))

            put("url", target.url)

            put("summary", target.summary)

            put("yield", target.yield)

            put("time", target.time)

            put("img", target.img)

            put("prep", target.prep)
        }

        val jsonObjectString = jsonObject.toString()

        return jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
    }
}