package com.inventaryos.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.inventaryos.domain.model.Product
import com.inventaryos.domain.usecase.product.GetProductImage
import com.inventaryos.domain.usecase.product.GetProducts
import com.inventaryos.ui.navigation.AppScreenNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProducts: GetProducts,
    private val getProductImage: GetProductImage
) : ViewModel() {
    var products by mutableStateOf(emptyList<Product>())
    val prodSelected = mutableStateListOf<Product>()
    var prodSearch by mutableStateOf("")
    var isSearching by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    fun loadProducts() {
        viewModelScope.launch {
            isLoading = true
            products = getProducts.invoke()
            isLoading = false
        }
    }

    fun selectProduct(prod: Product) {
        prodSelected.clear()
        prodSelected.add(prod)
    }

    fun unSelectedProduct(){
        prodSelected.clear()
    }

    fun removeSearch() {
        prodSearch = ""
        isSearching = false
        loadProducts()
    }

    fun navigateToAddItem(navController: NavController) {
        navController.navigate(AppScreenNavigation.AddProduct.route)
    }

    suspend fun getProdImg(prodId: String): Any? {
        return getProductImage.invoke(prodId)
    }
}