package com.inventaryos.presentation.addProduct

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
import com.inventaryos.domain.usecase.product.AddProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProduct: AddProduct
) : ViewModel() {
    var tittle by mutableStateOf("")
    var imageUri by mutableStateOf("")
    var barCode by mutableStateOf("")
    var quantity by mutableStateOf(0)
    var isLoading by mutableStateOf(false)

    fun save(context: Context) {
        viewModelScope.launch {
            isLoading = true
            if (tittle.isNotBlank() && imageUri.isNotBlank() && barCode.isNotBlank() && quantity != 0) {
                if (addProduct.invoke(Product(tittle, quantity, barCode), Uri.parse(imageUri))) {
                    Toast.makeText(context,"Producto agregado con exito",Toast.LENGTH_SHORT).show()
                    clear()
                }else{
                    Toast.makeText(context,"Hubo un error al agregar el producto",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Llene todos los campos",Toast.LENGTH_SHORT).show()
            }
            isLoading = false
        }
    }

    fun navigateToMain(navController: NavController){
        navController.popBackStack()
    }

    private fun clear(){
        tittle = ""
        imageUri = ""
        barCode = ""
        quantity = 0
    }
}