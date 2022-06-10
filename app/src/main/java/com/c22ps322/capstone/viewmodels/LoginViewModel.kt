package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.databinding.FragmentChangePasswordBinding
import com.c22ps322.capstone.repositories.AbstractUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: AbstractUserRepository
): ViewModel() {

    suspend fun login(email: String, password: String) =
        userRepository.login(email, password)

    fun isLoggedIn(): Boolean =
        userRepository.isLoggedIn()
}