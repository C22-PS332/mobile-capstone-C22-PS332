package com.c22ps322.mealsnap.repositories

import com.c22ps322.mealsnap.BuildConfig
import com.c22ps322.mealsnap.models.domain.Recipe
import com.c22ps322.mealsnap.models.enums.NetworkResult
import com.c22ps322.mealsnap.models.spoonacular.SpoonacularResponse
import com.c22ps322.mealsnap.modules.network.DomainFoodService
import com.c22ps322.mealsnap.modules.network.SpoonacularFoodService
import com.c22ps322.mealsnap.utils.getErrorMessageFromApi
import com.c22ps322.mealsnap.utils.reduceFileSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class FoodRepository @Inject constructor(
    override val foodService: DomainFoodService,
    override val spoonacularFoodService: SpoonacularFoodService
) : AbstractFoodRepository() {
    override suspend fun uploadIngredients(
        apiKey: String,
        file: File
    ): Flow<NetworkResult<ArrayList<Recipe>>> = flow {
        emit(NetworkResult.Loading)

        val reducedFile = reduceFileSize(file)

        val img = reducedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

        val multiPart = MultipartBody.Part.createFormData(
            "file",
            file.name,
            img
        )

        try {
            val response = foodService.uploadImage("Bearer $apiKey", multiPart)

            when (response.code()) {
                200 -> {
                    val result = arrayListOf<Recipe>()

                    response.body().orEmpty().forEach { result.add(it) }

                    emit(NetworkResult.Success(result))
                }

                else -> emit(NetworkResult.Error(getErrorMessageFromApi(response, "detail")))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecipeInformation(id: Int): Flow<NetworkResult<SpoonacularResponse>> =
        flow {
            emit(NetworkResult.Loading)

            try {
                val response: Response<SpoonacularResponse> =
                    spoonacularFoodService.getRecipeInformation(
                        BuildConfig.SPOONACULAR_API_KEY, id
                    )

                when (response.code()) {
                    200 -> emit(NetworkResult.Success(response.body()!!))

                    else -> emit(NetworkResult.Error(getErrorMessageFromApi(response, "message")))
                }

            } catch (e: Exception) {

                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

}