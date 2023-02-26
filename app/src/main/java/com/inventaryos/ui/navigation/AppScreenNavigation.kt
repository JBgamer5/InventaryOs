package com.inventaryos.ui.navigation

sealed class AppScreenNavigation(val route: String) {
    object Splash : AppScreenNavigation("SplashScreen")
    object Main : AppScreenNavigation("MainScreen")
    object AddProduct : AppScreenNavigation("AddProductScreen")
    object UpdateProduct : AppScreenNavigation("UpdateProductScreen")
}
