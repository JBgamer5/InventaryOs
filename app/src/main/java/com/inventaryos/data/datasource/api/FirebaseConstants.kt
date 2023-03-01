package com.inventaryos.data.datasource.api

sealed class FirebaseConstants(val route: String) {
    object Productos : FirebaseConstants("Productos")
    object Usuarios : FirebaseConstants("Usuarios")
    object Imagenes : FirebaseConstants("Imagenes")
}
