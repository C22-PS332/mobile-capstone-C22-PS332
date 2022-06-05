package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.repositories.AbstractFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ListRecipeViewModel @Inject constructor(
    private val foodRepository: AbstractFoodRepository
): ViewModel() {

    suspend fun uploadImage(file: File) = foodRepository.uploadIngredients("dummy", file)
}