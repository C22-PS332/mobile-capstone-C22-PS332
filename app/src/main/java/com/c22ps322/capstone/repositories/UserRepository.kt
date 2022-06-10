package com.c22ps322.capstone.repositories

import android.content.SharedPreferences
import android.util.Log
import com.c22ps322.capstone.models.domain.*
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.modules.network.UserService
import com.c22ps322.capstone.utils.NetworkMapperInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    override val userService: UserService,
    override val registerMapper: NetworkMapperInterface<RegisterRequestParam>,
    override val changePasswordMapper: NetworkMapperInterface<ChangePasswordRequestParam>,
    override val pref: SharedPreferences
) : AbstractUserRepository() {
    override suspend fun login(
        username: String,
        password: String
    ): Flow<NetworkResult<LoginResponse>> = flow {

        emit(NetworkResult.Loading)

        try {
            val response: Response<LoginResponse> = userService.login(username, password)

            val accessToken = response.body()?.token

            if (accessToken == null || !response.isSuccessful) {
//                emit(NetworkResult.Error(response.message()))
                Log.e("Login", response.message())
            } else {
//                emit(NetworkResult.Success(response.message()))
                setLogin(accessToken)
            }
        } catch (e: Exception) {
            Log.e("Login", e.message.toString())
        }
    }.flowOn(Dispatchers.IO)

    override fun isLoggedIn(): Boolean {
        val token = pref.getString(USER_TOKEN_KEY, null)

        return !token.isNullOrBlank()
    }

    override fun setLogin(accessToken: String) {
        pref.edit().apply {
            putString(USER_TOKEN_KEY, accessToken)

            apply()
        }
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
        }

    companion object {
        const val USER_TOKEN_KEY = "user_token_key"

        const val USER_EMAIL_KEY = "user_email_key"
    }
}