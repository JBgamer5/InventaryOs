package com.inventaryos.data.repository.product

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.inventaryos.data.datasource.api.FirebaseConstants
import com.inventaryos.data.datasource.api.product.ProductApiDataSource
import com.inventaryos.data.datasource.api.product.entity.ProductApiEntity
import com.inventaryos.ui.navigation.AppScreenNavigation
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductApiDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ProductApiDataSource {

    override suspend fun addProduct(productApiEntity: ProductApiEntity, uri: Uri): Boolean {
        return try {
            firestore.collection(FirebaseConstants.Productos.route)
                .document(productApiEntity.id_prod).set(productApiEntity).await()
            storage.reference.child(FirebaseConstants.Imagenes.route)
                .child(productApiEntity.id_prod).putFile(uri).await()
            true
        } catch (e: Exception) {
            Log.d("ProductApiDataSource", e.toString())
            false
        }
    }

    override suspend fun getProducts(): List<ProductApiEntity> {
        return try {
            firestore.collection(FirebaseConstants.Productos.route).get().await()
                .toObjects(ProductApiEntity::class.java)
        } catch (e: Exception) {
            Log.d("ProductApiDataSource", e.toString())
            emptyList()
        }
    }

    override suspend fun getImage(prodId: String): Any? {
        return try {
            storage.reference.child(FirebaseConstants.Imagenes.route)
                .child(prodId).downloadUrl.await()
        } catch (e: Exception) {
            Log.d("ProductApiDataSource", e.toString())
            null
        }
    }

    override suspend fun delProduct(prodId: String): Boolean {
        return try {
            firestore.collection(FirebaseConstants.Productos.route).document(prodId).delete()
                .await()
            storage.reference.child(FirebaseConstants.Imagenes.route).child(prodId).delete().await()
            true
        } catch (e: Exception) {
            Log.d("ProductApiDataSource", e.toString())
            false
        }
    }

    override suspend fun getProdById(prodId: String): ProductApiEntity? {
        return try {
            firestore.collection(FirebaseConstants.Productos.route).document(prodId).get().await()
                .toObject(ProductApiEntity::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateProd(productApiEntity: ProductApiEntity,uri: Uri): Boolean {
        return try {
            firestore.collection(FirebaseConstants.Productos.route).document(productApiEntity.id_prod).set(productApiEntity).await()
            storage.reference.child(FirebaseConstants.Imagenes.route).child(productApiEntity.id_prod).putFile(uri).await()
            true
        } catch (e: Exception){
            false
        }
    }

}