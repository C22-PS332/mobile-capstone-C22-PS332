package com.c22ps322.mealsnap.repositories

import com.c22ps322.mealsnap.models.domain.Recipe
import com.c22ps322.mealsnap.models.enums.NetworkResult
import com.c22ps322.mealsnap.models.spoonacular.SpoonacularResponse
import com.c22ps322.mealsnap.modules.network.DomainFoodService
import com.c22ps322.mealsnap.modules.network.SpoonacularFoodService
import kotlinx.coroutines.flow.Flow
import java.io.File

abstract class AbstractFoodRepository {
    abstract val foodService: DomainFoodService

    abstract val spoonacularFoodService: SpoonacularFoodService

    abstract suspend fun uploadIngredients(
        apiKey: String,
        file: File
    ): Flow<NetworkResult<ArrayList<Recipe>>>

    abstract suspend fun getRecipeInformation(id: Int): Flow<NetworkResult<SpoonacularResponse>>
}