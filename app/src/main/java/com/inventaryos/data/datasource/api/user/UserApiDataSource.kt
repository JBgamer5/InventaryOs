package com.inventaryos.data.datasource.api.user

import com.inventaryos.data.datasource.api.user.entity.UserApiEntity
import com.inventaryos.data.repository.user.UserApiDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


interface UserApiDataSource {
    suspend fun getCurrentUser(email: String): UserApiEntity?
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserApiDataSourceModule {

    @Binds
    abstract fun bindUserApiDataSource(userApiDataSourceImpl: UserApiDataSourceImpl): UserApiDataSource
}