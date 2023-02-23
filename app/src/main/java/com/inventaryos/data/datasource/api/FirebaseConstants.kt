package com.inventaryos.data.datasource.api

sealed class FirebaseConstants(val route: String) {
    object Productos : FirebaseConstants("Productos")
    object Imagenes : FirebaseConstants("Imagenes")
}
