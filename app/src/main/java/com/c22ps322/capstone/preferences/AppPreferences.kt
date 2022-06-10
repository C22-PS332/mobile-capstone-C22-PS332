package com.c22ps322.capstone.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.c22ps322.capstone.models.enums.CameraOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

import javax.inject.Inject

class AppPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun getCameraOption(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[CAMERA_KEY] ?: CameraOption.CAMERA_X
        }
    }

    suspend fun saveCameraOption(cameraOption: String) {
        dataStore.edit { preferences ->
            preferences[CAMERA_KEY] = cameraOption
        }
    }

    companion object {
        val CAMERA_KEY = stringPreferencesKey("camera_option")
    }
}