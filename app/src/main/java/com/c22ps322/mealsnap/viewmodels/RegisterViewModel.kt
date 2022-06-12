package com.c22ps322.mealsnap.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.mealsnap.models.domain.RegisterRequestParam
import com.c22ps322.mealsnap.repositories.AbstractUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: AbstractUserRepository
) : ViewModel() {

    suspend fun register(name: String, email: String, password: String) =
        userRepository.register(RegisterRequestParam(name, email, password))
}