package com.c22ps322.capstone.utils

import okhttp3.RequestBody

interface NetworkMapperInterface<T>{
    fun toJson(target: T): RequestBody
}