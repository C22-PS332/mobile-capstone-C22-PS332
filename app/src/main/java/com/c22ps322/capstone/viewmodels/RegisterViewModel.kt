package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.repositories.AbstractUserRepository
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    userRepository: AbstractUserRepository
): ViewModel()