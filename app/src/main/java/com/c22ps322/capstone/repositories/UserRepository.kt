package com.c22ps322.capstone.repositories

import com.c22ps322.capstone.models.domain.*
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.modules.network.UserService
import com.c22ps322.capstone.utils.NetworkMapperInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    override val userService: UserService,
    override val registerMapper: NetworkMapperInterface<RegisterRequestParam>,
    override val changePasswordMapper: NetworkMapperInterface<ChangePasswordRequestParam>
) : AbstractUserRepository() {
    override suspend fun login(
        username: String,
        password: String
    ): Flow<NetworkResult<LoginResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun register(registerRequestParam: RegisterRequestParam): Flow<NetworkResult<RegisterResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(changePasswordRequestParam: ChangePasswordRequestParam): Flow<NetworkResult<ChangePasswordResponse>> {
        TODO("Not yet implemented")
    }
}