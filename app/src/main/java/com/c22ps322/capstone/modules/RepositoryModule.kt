@file:Suppress("unused")

package com.c22ps322.capstone.modules

import com.c22ps322.capstone.models.domain.ChangePasswordRequestParam
import com.c22ps322.capstone.models.domain.RegisterRequestParam
import com.c22ps322.capstone.repositories.AbstractFoodRepository
import com.c22ps322.capstone.repositories.AbstractUserRepository
import com.c22ps322.capstone.repositories.FoodRepository
import com.c22ps322.capstone.repositories.UserRepository
import com.c22ps322.capstone.utils.ChangePasswordMapper
import com.c22ps322.capstone.utils.NetworkMapperInterface
import com.c22ps322.capstone.utils.RegisterMapper
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

    @ViewModelScoped
    @Binds
    abstract fun bindUserRepository(userRepository: UserRepository): AbstractUserRepository

    @ViewModelScoped
    @Binds
    abstract fun bindRegisterMapper(registerMapper: RegisterMapper): NetworkMapperInterface<RegisterRequestParam>

    @ViewModelScoped
    @Binds
    abstract fun bindChangePasswordMapper(changePasswordMapper: ChangePasswordMapper): NetworkMapperInterface<ChangePasswordRequestParam>
}