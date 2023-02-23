package com.inventaryos.data.datasource.api.product

import android.net.Uri
import com.inventaryos.data.datasource.api.product.entity.ProductApiEntity
import com.inventaryos.data.repository.product.ProductApiDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface ProductApiDataSource {

    suspend fun addProduct(productApiEntity: ProductApiEntity, uri: Uri): Boolean

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProductApiDataSourceModule {

    @Binds
    abstract fun bindProductApiDataSouerce(productApiDataSourceImpl: ProductApiDataSourceImpl): ProductApiDataSource
}