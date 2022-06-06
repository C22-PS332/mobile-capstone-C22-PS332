package com.c22ps322.capstone.repositories

import com.c22ps322.capstone.models.domain.DummyRecipe
import com.c22ps322.capstone.models.edamam.EdamamRequestParam
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.models.edamam.EdamamResponse
import com.c22ps322.capstone.models.spoonacular.SpoonacularResponse
import com.c22ps322.capstone.modules.network.DomainFoodService
import com.c22ps322.capstone.modules.network.EdamamFoodService
import com.c22ps322.capstone.modules.network.SpoonacularFoodService
import com.c22ps322.capstone.utils.NetworkMapperInterface
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

abstract class AbstractFoodRepository {
    abstract val foodService: DomainFoodService

    abstract val edamamFoodService: EdamamFoodService

    abstract val spoonacularFoodService: SpoonacularFoodService

    abstract val foodMapper: NetworkMapperInterface<EdamamRequestParam>

    abstract suspend fun uploadIngredients(apiKey: String, file: File): Flow<NetworkResult<ArrayList<DummyRecipe>>>

    abstract suspend fun getNutrition(apiKey: String, appId: String, ingredients: List<String>): Flow<NetworkResult<EdamamResponse>>

    abstract suspend fun getRecipeInformation(id: Int): Flow<NetworkResult<SpoonacularResponse>>

    abstract fun getErrorMessageFromApi(response: Response<*>): String
}