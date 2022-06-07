package com.c22ps322.capstone.repositories

import com.c22ps322.capstone.BuildConfig
import com.c22ps322.capstone.models.domain.DummyRecipe
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.models.spoonacular.SpoonacularResponse
import com.c22ps322.capstone.modules.network.DomainFoodService
import com.c22ps322.capstone.modules.network.SpoonacularFoodService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class FoodRepository @Inject constructor(
    override val foodService: DomainFoodService,
    override val spoonacularFoodService: SpoonacularFoodService
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
                    716429,
                    "Soto",
                    "Ini adalah soto",
                    "https://awsimages.detik.net.id/community/media/visual/2021/12/14/resep-soto-ayam-jawa_43.jpeg?w=700&q=90",
                    arrayListOf("1 cup rice", "10 oz chickpeas")
                )
            )
        }

        emit(NetworkResult.Success(dummyRecipeList))
    }

    override suspend fun getRecipeInformation(id: Int): Flow<NetworkResult<SpoonacularResponse>> = flow {
        emit(NetworkResult.Loading)

        try {
            val response: Response<SpoonacularResponse> = spoonacularFoodService.getRecipeInformation(
                BuildConfig.SPOONACULAR_API_KEY, id)

            when(response.code()){
                200 -> emit(NetworkResult.Success(response.body()!!))

                else -> emit(NetworkResult.Error(getErrorMessageFromApi(response)))
            }

        } catch (e: Exception) {

            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    override fun getErrorMessageFromApi(response: Response<*>) : String{
        val jsonObject = JSONObject(response.errorBody()?.charStream()?.readText().orEmpty())

        return jsonObject.getString("message").orEmpty()
    }
}