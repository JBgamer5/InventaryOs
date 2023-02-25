package com.inventaryos.domain.usecase.product

import com.inventaryos.domain.model.Product
import com.inventaryos.domain.repository.product.ProductRepository
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}