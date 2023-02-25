package com.inventaryos

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.inventaryos.presentation.addItem.AddItemView
import com.inventaryos.presentation.main.MainView
import com.inventaryos.presentation.splash.SplashView
import com.inventaryos.ui.navigation.AppScreenNavigation
import com.inventaryos.ui.theme.InventaryOsTheme
import com.inventaryos.ui.theme.darkMode
import com.inventaryos.ui.theme.lightMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            if (isSystemInDarkTheme()) {
                systemUiController.setSystemBarsColor(darkMode, darkIcons = false)
            } else {
                systemUiController.setSystemBarsColor(lightMode, darkIcons = true)
            }
            InventaryOsTheme {
                Content()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(
    showSystemUi = true, uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun Content() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = AppScreenNavigation.Splash.route
    ) {
        composable(AppScreenNavigation.Splash.route,
            exitTransition = {
                fadeOut(tween(1000))
            }) {
            SplashView(navController)
        }
        composable(AppScreenNavigation.Main.route,
            enterTransition = {
                when (initialState.destination.route) {
                    AppScreenNavigation.Splash.route -> fadeIn(tween(1000))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    AppScreenNavigation.AddProduct.route -> slideOutVertically(tween(1000)) {
                        -it
                    }
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    AppScreenNavigation.AddProduct.route -> slideInVertically(tween(1000)) {
                        -it
                    }
                    else -> null
                }
            }
        ) {
            MainView(navController)
        }
        composable(
            AppScreenNavigation.AddProduct.route,
            popExitTransition = {
                when (targetState.destination.route) {
                    AppScreenNavigation.Main.route -> slideOutVertically (tween(1000)){
                        it
                    }
                    else -> null
                }
            },
            enterTransition = {
                when (initialState.destination.route) {
                    AppScreenNavigation.Main.route -> slideInVertically (tween(1000)){
                        it
                    }
                    else -> null
                }
            }
        ) {
            AddItemView(navController)
        }
    }
}