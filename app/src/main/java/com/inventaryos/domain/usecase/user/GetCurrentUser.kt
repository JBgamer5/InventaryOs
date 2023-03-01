package com.inventaryos.domain.usecase.user

import com.google.firebase.auth.FirebaseAuth
import com.inventaryos.domain.model.User
import com.inventaryos.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val repository: UserRepository,
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(): User? {
        return auth.currentUser?.email?.let { repository.getCurrentUser(it) }
    }
}