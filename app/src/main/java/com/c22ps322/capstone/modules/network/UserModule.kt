package com.c22ps322.capstone.modules.network

import com.c22ps322.capstone.modules.DomainRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Singleton
    @Provides
    fun provideUserService(@DomainRetrofit retrofit: Retrofit.Builder): UserService =
        retrofit.build().create(UserService::class.java)
}