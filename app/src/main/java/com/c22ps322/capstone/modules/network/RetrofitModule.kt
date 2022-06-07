package com.c22ps322.capstone.modules.network

import com.c22ps322.capstone.modules.DomainRetrofit
import com.c22ps322.capstone.modules.SpoonacularRetrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    //TODO: remove when deploy release apk
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        return client.build()
    }

    @DomainRetrofit
    @Singleton
    @Provides
    fun provideDomainRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(DOMAIN_API_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)

    @SpoonacularRetrofit
    @Singleton
    @Provides
    fun providesSpoonacularRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(SPOONACULAR_API_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)

    private const val DOMAIN_API_URL = "https://api-url/"

    private const val SPOONACULAR_API_URL = "https://api.spoonacular.com/"
}