package com.inventaryos.data.repository.product

import android.net.Uri
import com.inventaryos.data.datasource.api.product.ProductApiDataSource
import com.inventaryos.data.datasource.api.product.entity.toProductApiEntity
import com.inventaryos.domain.model.Product
import com.inventaryos.domain.repository.product.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApiDataSource: ProductApiDataSource
): ProductRepository {

    override suspend fun addProduct(product: Product,uri: Uri) : Boolean{
        return productApiDataSource.addProduct(product.toProductApiEntity(),uri)
    }

}