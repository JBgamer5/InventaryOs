package com.inventaryos.data.datasource.api.user.entity

import com.inventaryos.domain.model.User

data class UserApiEntity(val nombre: String, val admin: Boolean){
    constructor() : this("",false)
}

fun UserApiEntity.toUser(): User {
    return User(nombre, admin)
}