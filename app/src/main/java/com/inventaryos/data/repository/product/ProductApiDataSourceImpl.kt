package com.inventaryos.data.repository.product

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.inventaryos.data.datasource.api.FirebaseConstants
import com.inventaryos.data.datasource.api.product.ProductApiDataSource
import com.inventaryos.data.datasource.api.product.entity.ProductApiEntity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductApiDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ProductApiDataSource {

    override suspend fun addProduct(productApiEntity: ProductApiEntity,uri: Uri): Boolean {
        return try {
            firestore.collection(FirebaseConstants.Productos.route)
                .document(productApiEntity.id_prod).set(productApiEntity).await()
            storage.reference.child(FirebaseConstants.Imagenes.route).child(productApiEntity.id_prod).putFile(uri).await()
            true
        } catch (e: java.lang.Exception) {
            Log.d("ProductApiDataSource", e.toString())
            false
        }
    }

}