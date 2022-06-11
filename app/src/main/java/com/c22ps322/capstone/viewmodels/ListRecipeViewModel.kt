package com.c22ps322.capstone.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps322.capstone.models.domain.Recipe
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.repositories.AbstractFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ListRecipeViewModel @Inject constructor(
    private val foodRepository: AbstractFoodRepository
): ViewModel() {

    var uploadFlow: Flow<NetworkResult<ArrayList<Recipe>>> = flowOf()

    private val _startCollectFlow = MutableLiveData(false)

    val startCollectFlow: LiveData<Boolean> = _startCollectFlow

    suspend fun uploadImage(file: File) {

        _startCollectFlow.value = true

        uploadFlow = flow {
            val source = foodRepository.uploadIngredients("api", file)

            emitAll(source)
        }
    }

    fun doneCollectFlow(){
        _startCollectFlow.value = false
    }
}