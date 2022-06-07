package com.c22ps322.capstone.modules

import com.c22ps322.capstone.repositories.AbstractFoodRepository
import com.c22ps322.capstone.repositories.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @ViewModelScoped
    @Binds
    abstract fun bindFoodRepository(foodRepository: FoodRepository): AbstractFoodRepository
}