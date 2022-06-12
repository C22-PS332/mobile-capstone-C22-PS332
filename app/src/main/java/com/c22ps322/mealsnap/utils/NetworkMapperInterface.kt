package com.c22ps322.mealsnap.utils

import okhttp3.RequestBody

interface NetworkMapperInterface<T> {
    fun toJson(target: T): RequestBody
}