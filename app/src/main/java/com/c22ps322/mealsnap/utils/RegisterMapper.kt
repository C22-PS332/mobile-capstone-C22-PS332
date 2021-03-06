package com.c22ps322.mealsnap.utils

import com.c22ps322.mealsnap.models.domain.RegisterRequestParam
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class RegisterMapper @Inject constructor() : NetworkMapperInterface<RegisterRequestParam> {
    override fun toJson(target: RegisterRequestParam): RequestBody {
        val jsonObject = JSONObject()

        jsonObject.apply {
            put("email", target.email)

            put("password", target.password)

            put("name", target.name)
        }

        val jsonObjectString = jsonObject.toString()

        return jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
    }
}