package com.c22ps322.mealsnap.utils

import com.c22ps322.mealsnap.models.domain.ChangePasswordRequestParam
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class ChangePasswordMapper @Inject constructor() :
    NetworkMapperInterface<ChangePasswordRequestParam> {
    override fun toJson(target: ChangePasswordRequestParam): RequestBody {
        val jsonObject = JSONObject()

        jsonObject.apply {
            put("email", target.email)

            put("password", target.password)

            put("newPassword", target.newPassword)
        }

        val jsonObjectString = jsonObject.toString()

        return jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
    }
}