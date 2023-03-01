package com.inventaryos.domain.repository

import com.inventaryos.data.repository.user.UserRepositoryImpl
import com.inventaryos.domain.model.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface UserRepository {
    suspend fun getCurrentUser(email: String): User?
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserRepositoryModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}