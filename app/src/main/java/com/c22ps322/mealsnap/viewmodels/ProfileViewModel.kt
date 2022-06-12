package com.c22ps322.mealsnap.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.mealsnap.models.domain.ChangePasswordRequestParam
import com.c22ps322.mealsnap.repositories.AbstractUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: AbstractUserRepository
) : ViewModel() {

    suspend fun changePassword(email: String, password: String, newPassword: String) =
        userRepository.changePassword(ChangePasswordRequestParam(email, password, newPassword))

    fun logout() = userRepository.logout()

    fun getEmail() = userRepository.getEmail()
}