package com.c22ps322.capstone.repositories

import com.c22ps322.capstone.models.domain.*
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.modules.network.UserService
import com.c22ps322.capstone.utils.NetworkMapperInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
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

    override suspend fun changePassword(changePasswordRequestParam: ChangePasswordRequestParam): Flow<NetworkResult<ChangePasswordResponse>> =
        flow {
            emit(NetworkResult.Loading)

            try {

                val requestBody = changePasswordMapper.toJson(changePasswordRequestParam)

                val response = userService.changePassword(requestBody)

                when (response.code()) {
                    200 -> emit(NetworkResult.Success(response.body()!!))

                    else -> {
                        val jsonObject =
                            JSONObject(response.errorBody()?.charStream()?.readText().orEmpty())

                        val message = jsonObject.getString("detail").orEmpty()

                        emit(NetworkResult.Error(message))
                    }
                }

            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
}