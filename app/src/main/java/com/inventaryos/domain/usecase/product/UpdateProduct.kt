package com.inventaryos.domain.usecase.product

import android.net.Uri
import com.inventaryos.domain.model.Product
import com.inventaryos.domain.repository.product.ProductRepository
import javax.inject.Inject

class UpdateProduct @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(prod: Product, uri: Uri) {
        repository.updateProd(prod, uri)
    }
}