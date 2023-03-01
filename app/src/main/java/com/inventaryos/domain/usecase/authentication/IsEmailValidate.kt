package com.inventaryos.domain.usecase.authentication

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class IsEmailValidate @Inject constructor(
    private val auth: FirebaseAuth
) {
    operator fun invoke(): Boolean {
        return try {
            val res = auth.currentUser?.isEmailVerified ?: false
            if (!res) {
                auth.currentUser?.sendEmailVerification()
            }
            res
        } catch (e: Exception) {
            Log.d("IsEmailValidate", e.toString())
            false
        }
    }
}