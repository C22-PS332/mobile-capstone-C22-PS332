package com.c22ps322.mealsnap.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c22ps322.mealsnap.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {
    fun getCameraOption() = appPreferences.getCameraOption().asLiveData()

    fun saveCameraOption(cameraOption: String) {
        viewModelScope.launch {
            appPreferences.saveCameraOption(cameraOption)
        }
    }

    fun isFirstTime() = appPreferences.isFirstOpen().asLiveData()

    fun setAfterFirstOpen() {
        viewModelScope.launch {
            appPreferences.setAfterFirstOpen()
        }
    }
}