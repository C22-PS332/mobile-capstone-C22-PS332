package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c22ps322.capstone.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appPreferences: AppPreferences
): ViewModel() {
    fun getCameraOption() = appPreferences.getCameraOption().asLiveData()

    fun saveCameraOption(cameraOption: String) {
        viewModelScope.launch {
            appPreferences.saveCameraOption(cameraOption)
        }
    }
}