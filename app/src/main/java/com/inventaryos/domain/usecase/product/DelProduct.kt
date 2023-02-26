package com.inventaryos.domain.usecase.product

import com.inventaryos.domain.repository.product.ProductRepository
import javax.inject.Inject

class DelProduct @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(prodId: String): Boolean {
        return repository.delProduct(prodId)
    }
}