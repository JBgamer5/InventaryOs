package com.inventaryos.domain.usecase.authentication

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginWithEmailAndPassword @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(correo: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(correo, password).await().user != null
        } catch (e: Exception) {
            Log.d("Authentication", e.toString())
            false
        }
    }
}