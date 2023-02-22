package com.inventaryos.data.datasource.remote

sealed class FirebaseConstants(val route: String) {
    object Productos : FirebaseConstants("Productos")
}
