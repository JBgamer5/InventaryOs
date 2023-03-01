package com.inventaryos.data.repository.user

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.inventaryos.data.datasource.api.FirebaseConstants
import com.inventaryos.data.datasource.api.user.UserApiDataSource
import com.inventaryos.data.datasource.api.user.entity.UserApiEntity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserApiDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserApiDataSource {
    override suspend fun getCurrentUser(email: String): UserApiEntity? {
        return try {
            firestore.collection(FirebaseConstants.Usuarios.route).document(email).get().await()
                .toObject(UserApiEntity::class.java)
        } catch (e: Exception) {
            Log.d("UserApiDataSource", e.toString())
            null
        }
    }

}