package com.inventaryos.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.inventaryos.domain.usecase.authentication.IsEmailValidate
import com.inventaryos.domain.usecase.authentication.LoginWithEmailAndPassword
import com.inventaryos.ui.navigation.AppScreenNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val isEmailValidate: IsEmailValidate,
    private val loginWithEmailAndPassword: LoginWithEmailAndPassword
) : ViewModel() {
    var correo by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun login(context: Context, navController: NavController) {
        if (correo.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch {
                isLoading = true
                if (loginWithEmailAndPassword(correo, password)) {
                    if (isEmailValidate()) {
                        isLoading = false
                        navController.popBackStack()
                        navController.navigate(AppScreenNavigation.Main.route)
                    } else {
                        Toast.makeText(
                            context,
                            "El correo no está verificado aún",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    Toast.makeText(context, "No se encuentra registrado", Toast.LENGTH_SHORT).show()
                }
                isLoading = false
            }
        } else {
            Toast.makeText(context, "Llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}