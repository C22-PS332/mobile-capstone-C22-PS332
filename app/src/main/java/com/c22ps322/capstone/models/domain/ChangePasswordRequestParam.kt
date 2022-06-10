package com.c22ps322.capstone.models.domain

data class ChangePasswordRequestParam(
    val email: String,
    val password: String,
    val newPassword: String
)