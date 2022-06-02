package com.c22ps322.capstone.repositories

import com.c22ps322.capstone.models.domain.DummyRecipe
import com.c22ps322.capstone.models.edamam.EdamamRequestParam
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.models.edamam.EdamamResponse
import com.c22ps322.capstone.modules.network.DomainFoodService
import com.c22ps322.capstone.modules.network.EdamamFoodService
import com.c22ps322.capstone.utils.NetworkMapperInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class FoodRepository @Inject constructor(
    override val foodService: DomainFoodService,
    override val edamamFoodService: EdamamFoodService,
    override val foodMapper: NetworkMapperInterface<EdamamRequestParam>
) : AbstractFoodRepository() {
    override suspend fun uploadIngredients(
        apiKey: String,
        file: File
    ): Flow<NetworkResult<ArrayList<DummyRecipe>>> = flow {
        emit(NetworkResult.Loading)

        val dummyRecipeList = arrayListOf<DummyRecipe>()

        for (i in 0..3) {
            dummyRecipeList.add(
                DummyRecipe(
                    "Soto",
                    "Ini adalah soto",
                    "https://awsimages.detik.net.id/community/media/visual/2021/12/14/resep-soto-ayam-jawa_43.jpeg?w=700&q=90",
                    listOf()
                )
            )
        }

        emit(NetworkResult.Success(dummyRecipeList))
    }

    override suspend fun getNutrition(
        apiKey: String,
        appId: String,
        ingredients: List<String>
    ): Flow<NetworkResult<EdamamResponse>> = flow {

        emit(NetworkResult.Loading)

        try {
            val response: Response<EdamamResponse> = edamamFoodService.getNutrition(
                appId,
                apiKey,
                foodMapper.toJson(EdamamRequestParam(ingredients))
            )

            emit(NetworkResult.Success(response.body()!!))

        } catch (e: Exception) {

            emit(NetworkResult.Error(e.message.toString()))
        }
    }
}