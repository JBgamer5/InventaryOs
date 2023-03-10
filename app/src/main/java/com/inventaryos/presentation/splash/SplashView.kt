package com.inventaryos.presentation.splash

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.inventaryos.R
import com.inventaryos.ui.navigation.AppScreenNavigation
import com.inventaryos.ui.theme.*
import kotlinx.coroutines.delay

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun Preview() {
    val navController = rememberNavController()
    SplashView(navController)
}

@Composable
fun SplashView(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(2500)
        navController.popBackStack()
        navController.navigate(AppScreenNavigation.Login.route)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) darkMode else lightMode),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = if (isSystemInDarkTheme()) greenLight else cian,
            shape = CircleShape,
            modifier = Modifier
                .size(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash),
                contentDescription = "ic_splash",
                modifier = Modifier
                    .padding(30.dp)
            )
        }
    }
}