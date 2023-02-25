package com.inventaryos.domain.usecase.product

import com.inventaryos.domain.repository.product.ProductRepository
import javax.inject.Inject

class GetProductImage @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(prodId: String): Any? {
        return repository.getImage(prodId)
    }
}