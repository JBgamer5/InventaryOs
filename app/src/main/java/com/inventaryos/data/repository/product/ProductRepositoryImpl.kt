package com.inventaryos.data.repository.product

import android.net.Uri
import com.inventaryos.data.datasource.api.product.ProductApiDataSource
import com.inventaryos.data.datasource.api.product.entity.toProduct
import com.inventaryos.data.datasource.api.product.entity.toProductApiEntity
import com.inventaryos.domain.model.Product
import com.inventaryos.domain.repository.product.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApiDataSource: ProductApiDataSource
) : ProductRepository {

    override suspend fun addProduct(product: Product, uri: Uri): Boolean {
        return productApiDataSource.addProduct(product.toProductApiEntity(), uri)
    }

    override suspend fun getProducts(): List<Product> {
        return productApiDataSource.getProducts().map { it.toProduct() }
    }

    override suspend fun getImage(prodId: String): Any? {
        return productApiDataSource.getImage(prodId)
    }

    override suspend fun delProduct(prodId: String): Boolean {
        return productApiDataSource.delProduct(prodId)
    }

    override suspend fun getProdById(prodId: String): Product? {
        return productApiDataSource.getProdById(prodId)?.toProduct()
    }

    override suspend fun updateProd(product: Product, uri: Uri): Boolean {
        return productApiDataSource.updateProd(product.toProductApiEntity(), uri)
    }

}