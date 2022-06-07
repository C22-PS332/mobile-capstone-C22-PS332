package com.c22ps322.capstone.repositories

import com.c22ps322.capstone.models.domain.DummyRecipe
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.models.spoonacular.SpoonacularResponse
import com.c22ps322.capstone.modules.network.DomainFoodService
import com.c22ps322.capstone.modules.network.SpoonacularFoodService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

abstract class AbstractFoodRepository {
    abstract val foodService: DomainFoodService

    abstract val spoonacularFoodService: SpoonacularFoodService

    abstract suspend fun uploadIngredients(apiKey: String, file: File): Flow<NetworkResult<ArrayList<DummyRecipe>>>

    abstract suspend fun getRecipeInformation(id: Int): Flow<NetworkResult<SpoonacularResponse>>

    abstract fun getErrorMessageFromApi(response: Response<*>): String
}