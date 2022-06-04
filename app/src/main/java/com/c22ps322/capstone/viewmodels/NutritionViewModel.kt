package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.BuildConfig
import com.c22ps322.capstone.repositories.AbstractFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val foodRepository: AbstractFoodRepository
) : ViewModel() {

    suspend fun getNutrition(ingredients: List<String>) = foodRepository.getNutrition(
        apiKey = BuildConfig.EDAMAM_API_KEY,
        appId = BuildConfig.EDAMAM_APP_ID,
        ingredients = ingredients
    )
}