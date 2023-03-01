package com.inventaryos.presentation.login

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.inventaryos.R
import com.inventaryos.ui.theme.*

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun Preview() {
    val navController = rememberNavController()
    LoginView(navController)
}

@Composable
fun LoginView(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val focus = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(
            color = if (isSystemInDarkTheme()) black else lightMode,
            shape = CircleShape,
            elevation = 10.dp,
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_login),
                contentDescription = "ic_login",
                tint = if (isSystemInDarkTheme()) greenLight else cian,
                modifier = Modifier
                    .padding(10.dp)
                    .size(120.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Surface(
            color = Color.Unspecified,
            elevation = 7.dp,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 15.dp)
        ) {
            TextField(
                value = viewModel.correo,
                onValueChange = {
                    viewModel.correo = it.trim().lowercase()
                },
                placeholder = {
                    Text(text = "Escriba su correo")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = if (isSystemInDarkTheme()) black else lightMode,
                    placeholderColor = if (isSystemInDarkTheme()) lightMode.copy(.6f) else darkMode.copy(
                        .6f
                    ),
                    textColor = if (isSystemInDarkTheme()) lightMode else darkMode,
                    cursorColor = if (isSystemInDarkTheme()) greenLight else cian,
                    focusedIndicatorColor = if (isSystemInDarkTheme()) greenLight else cian,
                    unfocusedIndicatorColor = Color.Unspecified,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focus.moveFocus(
                            FocusDirection.Down
                        )
                    }
                )
            )
        }
        Surface(
            color = Color.Unspecified,
            elevation = 7.dp,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 15.dp)
        ) {
            TextField(
                value = viewModel.password,
                onValueChange = {
                    viewModel.password = it
                },
                placeholder = {
                    Text(text = "Escriba su contraseña")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = if (isSystemInDarkTheme()) black else lightMode,
                    placeholderColor = if (isSystemInDarkTheme()) lightMode.copy(.6f) else darkMode.copy(
                        .6f
                    ),
                    textColor = if (isSystemInDarkTheme()) lightMode else darkMode,
                    cursorColor = if (isSystemInDarkTheme()) greenLight else cian,
                    focusedIndicatorColor = if (isSystemInDarkTheme()) greenLight else cian,
                    unfocusedIndicatorColor = Color.Unspecified,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    focus.clearFocus()
                }),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                viewModel.login(context, navController)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isSystemInDarkTheme()) greenLight else cian,
                contentColor = if (isSystemInDarkTheme()) darkMode else lightMode
            )
        ) {
            Text(text = "Iniciar Sesion")
        }
    }
}