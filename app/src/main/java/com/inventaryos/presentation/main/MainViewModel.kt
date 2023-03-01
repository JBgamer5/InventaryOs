package com.inventaryos.presentation.main

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.inventaryos.domain.model.Product
import com.inventaryos.domain.model.User
import com.inventaryos.domain.usecase.product.DelProduct
import com.inventaryos.domain.usecase.product.GetProdById
import com.inventaryos.domain.usecase.product.GetProductImage
import com.inventaryos.domain.usecase.product.GetProducts
import com.inventaryos.domain.usecase.user.GetCurrentUser
import com.inventaryos.ui.navigation.AppScreenNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProducts: GetProducts,
    private val getProductImage: GetProductImage,
    private val delProduct: DelProduct,
    private val getProdById: GetProdById,
    private val getCurrentUser: GetCurrentUser
) : ViewModel() {
    var currentUser: User? by mutableStateOf(null)
    var isAdmin by mutableStateOf(false)
    var products = mutableStateListOf<Product>()
    var isSearching by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    fun init(context: Context) {
        viewModelScope.launch {
            isLoading = true
            currentUser = getCurrentUser()
            if (currentUser != null) {
                currentUser?.let {
                    isAdmin = it.admin
                }
                loadProducts()
            } else {
                Toast.makeText(
                    context,
                    "Ha ocurrido un error en la conexion, reintente mas tarde",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            isLoading = true
            products = getProducts().asReversed().toMutableStateList()
            isSearching = false
            isLoading = false
        }
    }

    fun searchProduct(prodId: String) {
        viewModelScope.launch {
            isLoading = true
            isSearching = true
            products.clear()
            val prod = getProdById(prodId)
            if (prod != null) {
                products.add(prod)
            }
            isLoading = false
        }
    }

    fun navigateToAddItem(navController: NavController) {
        navController.navigate(AppScreenNavigation.AddProduct.route)
    }

    suspend fun getProdImg(prodId: String): Any? {
        return getProductImage(prodId)
    }

    fun deleteProduct(prodId: String, context: Context) {
        viewModelScope.launch {
            isLoading = true
            delProduct(prodId)
            isLoading = false
            Toast.makeText(context, "El producto se ha eliminado con exito", Toast.LENGTH_SHORT)
                .show()
            loadProducts()
        }
    }

    fun navigateToUpdate(prodId: String, navController: NavController) {
        navController.navigate("${AppScreenNavigation.UpdateProduct.route}/$prodId")
    }
}