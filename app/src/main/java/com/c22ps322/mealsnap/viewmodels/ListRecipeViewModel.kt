package com.c22ps322.mealsnap.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps322.mealsnap.models.domain.Recipe
import com.c22ps322.mealsnap.models.enums.NetworkResult
import com.c22ps322.mealsnap.repositories.AbstractFoodRepository
import com.c22ps322.mealsnap.repositories.AbstractUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ListRecipeViewModel @Inject constructor(
    private val foodRepository: AbstractFoodRepository,

    private val userRepository: AbstractUserRepository
) : ViewModel() {

    var uploadFlow: Flow<NetworkResult<ArrayList<Recipe>>> = flowOf()

    private val _startCollectFlow = MutableLiveData(false)

    val startCollectFlow: LiveData<Boolean> = _startCollectFlow

    suspend fun uploadImage(token: String, file: File) {

        _startCollectFlow.value = true

        uploadFlow = flow {
            val source = foodRepository.uploadIngredients(token, file)

            emitAll(source)
        }
    }

    fun doneCollectFlow() {
        _startCollectFlow.value = false
    }

    fun getToken() = userRepository.getToken()
}