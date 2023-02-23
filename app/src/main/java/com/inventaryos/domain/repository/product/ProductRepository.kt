package com.inventaryos.domain.repository.product

import android.net.Uri
import com.inventaryos.data.repository.product.ProductRepositoryImpl
import com.inventaryos.domain.model.Product
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface ProductRepository {
    suspend fun addProduct(product: Product, uri: Uri): Boolean
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProductRepositoryModule {

    @Binds
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}