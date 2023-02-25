package com.inventaryos.data.datasource.api.product.entity

import com.inventaryos.domain.model.Product

data class ProductApiEntity(val nombre: String, val cantidad: Int, val id_prod: String){
    constructor() : this("",0,"")
}

fun ProductApiEntity.toProduct() : Product{
    return Product(nombre,cantidad,id_prod)
}

fun Product.toProductApiEntity() : ProductApiEntity{
    return ProductApiEntity(nombre,cantidad,id_prod)
}
