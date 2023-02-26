package com.inventaryos.presentation.updateProduct

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.inventaryos.domain.model.Product
import com.inventaryos.domain.usecase.product.GetProdById
import com.inventaryos.domain.usecase.product.GetProductImage
import com.inventaryos.domain.usecase.product.UpdateProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProductViewModel @Inject constructor(
    private val getProductImage: GetProductImage,
    private val getProdById: GetProdById,
    private val updateProduct: UpdateProduct
) : ViewModel() {
    var tittle by mutableStateOf("")
    var quantity by mutableStateOf(0)
    var imgUri by mutableStateOf("")
    var barCode by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun navigateToMain(navController: NavController) {
        navController.popBackStack()
    }

    fun loadProduct(prodId: String) {
        viewModelScope.launch {
            isLoading = true
            val prod = getProdById.invoke(prodId)
            if (prod != null) {
                tittle = prod.nombre
                quantity = prod.cantidad
                val img = getProductImage.invoke(prod.id_prod)
                imgUri = img.toString()
                barCode = prodId
            }
            isLoading = false
        }
    }

    fun update(context: Context, navController: NavController) {
        viewModelScope.launch {
            isLoading = true
            updateProduct.invoke(Product(tittle, quantity, barCode), Uri.parse(imgUri))
            isLoading = false
            Toast.makeText(context, "El producto se ha actualizado con exito", Toast.LENGTH_SHORT)
                .show()
            navigateToMain(navController)
        }
    }
}