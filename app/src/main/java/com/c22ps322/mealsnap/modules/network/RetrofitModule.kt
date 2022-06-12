package com.c22ps322.mealsnap.modules.network

import com.c22ps322.mealsnap.modules.DomainRetrofit
import com.c22ps322.mealsnap.modules.SpoonacularRetrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideMoshiConverter(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @DomainRetrofit
    @Singleton
    @Provides
    fun provideDomainRetrofit(moshi: Moshi): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(DOMAIN_API_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    @SpoonacularRetrofit
    @Singleton
    @Provides
    fun providesSpoonacularRetrofit(moshi: Moshi): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(SPOONACULAR_API_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    private const val DOMAIN_API_URL = "https://bangkit-vm27eldwkq-uc.a.run.app/api/"

    private const val SPOONACULAR_API_URL = "https://api.spoonacular.com/"
}