package com.inventaryos.data.repository.user

import com.inventaryos.data.datasource.api.user.UserApiDataSource
import com.inventaryos.data.datasource.api.user.entity.toUser
import com.inventaryos.domain.model.User
import com.inventaryos.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiDataSource: UserApiDataSource
) : UserRepository {
    override suspend fun getCurrentUser(email: String): User? {
        return userApiDataSource.getCurrentUser(email)?.toUser()
    }
}