package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.models.domain.ChangePasswordRequestParam
import com.c22ps322.capstone.models.domain.RegisterRequestParam
import com.c22ps322.capstone.repositories.AbstractUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: AbstractUserRepository
): ViewModel() {

    suspend fun register(name: String, email: String, password: String) =
        userRepository.register(RegisterRequestParam(name, email, password))
}