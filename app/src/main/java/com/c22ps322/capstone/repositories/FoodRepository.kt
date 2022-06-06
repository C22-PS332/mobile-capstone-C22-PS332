package com.c22ps322.capstone.repositories

import com.c22ps322.capstone.BuildConfig
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
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class FoodRepository @Inject constructor(
    override val foodService: DomainFoodService,
    override val edamamFoodService: EdamamFoodService,
    override val foodMapper: NetworkMapperInterface<EdamamRequestParam>,
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

            when(response.code()){
                200 -> emit(NetworkResult.Success(response.body()!!))

                401 -> emit(NetworkResult.Error("Unauthorized"))

                404 -> emit(NetworkResult.Error("The specified URL was not found or couldn't be retrieved"))

                409 -> emit(NetworkResult.Error("The provided ETag token does not match the input data"))

                422 -> emit(NetworkResult.Error("Couldn't parse the recipe or extract the nutritional info"))

                555 -> emit(NetworkResult.Error("Recipe with insufficient quality to process correctly"))
            }

        } catch (e: Exception) {

            emit(NetworkResult.Error(e.message.toString()))
        }
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