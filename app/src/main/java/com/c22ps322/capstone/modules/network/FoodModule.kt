package com.c22ps322.capstone.modules.network

import com.c22ps322.capstone.modules.DomainRetrofit
import com.c22ps322.capstone.modules.EdamamRetrofit
import com.c22ps322.capstone.modules.SpoonacularRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FoodModule {

    @Singleton
    @Provides
    fun provideDomainService(@DomainRetrofit retrofit: Retrofit.Builder): DomainFoodService =
        retrofit.build().create(DomainFoodService::class.java)


    @Singleton
    @Provides
    fun provideEdamamService(@EdamamRetrofit retrofit: Retrofit.Builder): EdamamFoodService =
        retrofit.build().create(EdamamFoodService::class.java)


    @Singleton
    @Provides
    fun provideSpoonacularService(@SpoonacularRetrofit retrofit: Retrofit.Builder): SpoonacularFoodService =
        retrofit.build().create(SpoonacularFoodService::class.java)
}