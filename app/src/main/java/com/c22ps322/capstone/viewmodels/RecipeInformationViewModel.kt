package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.repositories.AbstractFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeInformationViewModel @Inject constructor(
    private val foodRepository: AbstractFoodRepository
): ViewModel() {

    suspend fun getRecipeInformation(id: Int) = foodRepository.getRecipeInformation(id)
}