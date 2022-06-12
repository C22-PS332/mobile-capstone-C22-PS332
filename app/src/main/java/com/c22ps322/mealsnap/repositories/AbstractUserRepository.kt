package com.c22ps322.mealsnap.repositories

import android.content.SharedPreferences
import com.c22ps322.mealsnap.models.domain.*
import com.c22ps322.mealsnap.models.enums.NetworkResult
import com.c22ps322.mealsnap.modules.network.UserService
import com.c22ps322.mealsnap.utils.NetworkMapperInterface
import kotlinx.coroutines.flow.Flow

abstract class AbstractUserRepository {

    abstract val userService: UserService

    abstract val registerMapper: NetworkMapperInterface<RegisterRequestParam>

    abstract val changePasswordMapper: NetworkMapperInterface<ChangePasswordRequestParam>

    abstract val pref: SharedPreferences

    abstract suspend fun login(
        username: String,
        password: String
    ): Flow<NetworkResult<LoginResponse>>

    abstract fun isLoggedIn(): Boolean

    abstract suspend fun register(
        registerRequestParam: RegisterRequestParam
    ): Flow<NetworkResult<RegisterResponse>>

    abstract suspend fun changePassword(
        changePasswordRequestParam: ChangePasswordRequestParam
    ): Flow<NetworkResult<ChangePasswordResponse>>

    abstract fun logout()

    abstract fun getEmail(): String?

    abstract fun getToken(): String?
}