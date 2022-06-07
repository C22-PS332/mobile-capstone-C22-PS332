package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.repositories.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeInformationViewModel @Inject constructor(
    private val foodRepository: FoodRepository
): ViewModel() {

    suspend fun getRecipeInformation(id: Int) = foodRepository.getRecipeInformation(id)
}