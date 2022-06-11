package com.c22ps322.capstone.repositories

import android.content.SharedPreferences
import com.c22ps322.capstone.models.domain.*
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.modules.network.UserService
import com.c22ps322.capstone.utils.NetworkMapperInterface
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

    abstract fun setLogin(email: String, accessToken: String)

    abstract suspend fun register(
        registerRequestParam: RegisterRequestParam
    ): Flow<NetworkResult<RegisterResponse>>

    abstract suspend fun changePassword(
        changePasswordRequestParam: ChangePasswordRequestParam
    ): Flow<NetworkResult<ChangePasswordResponse>>

    abstract fun logout()

    abstract fun getEmail(): String?

    abstract fun getToken(): String?

    abstract fun getBearer(): String
}