package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.repositories.AbstractUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    userRepository: AbstractUserRepository
): ViewModel()