package com.c22ps322.mealsnap.models.domain

data class ChangePasswordRequestParam(
    val email: String,
    val password: String,
    val newPassword: String
)