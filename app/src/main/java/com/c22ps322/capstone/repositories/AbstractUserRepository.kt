package com.c22ps322.capstone.repositories

import com.c22ps322.capstone.models.domain.*
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.modules.network.UserService
import com.c22ps322.capstone.utils.NetworkMapperInterface
import kotlinx.coroutines.flow.Flow

abstract class AbstractUserRepository {

    abstract val userService: UserService

    abstract val registerMapper: NetworkMapperInterface<RegisterRequestParam>

    abstract val changePasswordMapper: NetworkMapperInterface<ChangePasswordRequestParam>

    abstract suspend fun login(
        username: String,
        password: String
    ): Flow<NetworkResult<LoginResponse>>

    abstract suspend fun register(
        registerRequestParam: RegisterRequestParam
    ): Flow<NetworkResult<RegisterResponse>>

    abstract suspend fun changePassword(
        changePasswordRequestParam: ChangePasswordRequestParam
    ): Flow<NetworkResult<ChangePasswordResponse>>
}