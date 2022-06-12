package com.c22ps322.mealsnap.models.domain

data class RegisterRequestParam(
    val name: String,
    val email: String,
    val password: String
)
