package com.inventaryos.presentation.main

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.inventaryos.R
import com.inventaryos.domain.model.Product
import com.inventaryos.ui.theme.*
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.runBlocking

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun Preview() {
    val navController = rememberNavController()
    MainView(navController)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.init(context)
    }
    Scaffold(topBar = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "InventaryOs",
                fontSize = 55.sp,
                fontFamily = Lobster,
                color = if (isSystemInDarkTheme()) greenLight else cian
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 25.dp)
            ) {
                val scanLauncher =
                    rememberLauncherForActivityResult(
                        contract = ScanContract(),
                        onResult = { result ->
                            if (!result.contents.isNullOrEmpty()) {
                                viewModel.searchProduct(result.contents)
                            }
                        })
                Button(
                    onClick = {
                        if (!viewModel.isSearching) {
                            scanLauncher.launch(
                                ScanOptions().setPrompt("Acerque el codigo de barras")
                                    .setDesiredBarcodeFormats(ScanOptions.PRODUCT_CODE_TYPES)
                            )
                        } else {
                            viewModel.loadProducts()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Unspecified
                    ),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp)
                ) {
                    Icon(
                        imageVector = if (!viewModel.isSearching) Icons.Filled.Search else Icons.Filled.Close,
                        contentDescription = "ic_search",
                        modifier = Modifier.size(35.dp),
                        tint = if (isSystemInDarkTheme()) lightMode else darkMode
                    )
                }
            }
        }
    }, floatingActionButton = {
        if (viewModel.isAdmin) {
            FloatingActionButton(
                onClick = {
                    viewModel.navigateToAddItem(navController)
                },
                backgroundColor = if (isSystemInDarkTheme()) greenLight else cian
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "ic_add_product",
                    tint = if (isSystemInDarkTheme()) darkMode else lightMode,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }

    }, backgroundColor = if (isSystemInDarkTheme()) darkMode else lightMode
    ) {
        val state = rememberPullRefreshState(
            refreshing = viewModel.isLoading,
            onRefresh = {
                if (!viewModel.isSearching) {
                    viewModel.loadProducts()
                }
            })
        Box(
            modifier = Modifier
                .padding(it)
                .pullRefresh(state)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(viewModel.products) { item ->
                    Item(item, viewModel, navController)
                }
            }
            if (viewModel.products.isEmpty()) {
                Text(
                    text = "No se ha añadido ningún producto aún",
                    color = if (isSystemInDarkTheme()) lightMode else darkMode,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            PullRefreshIndicator(
                refreshing = true,
                state = state,
                backgroundColor = if (isSystemInDarkTheme()) darkMode else lightMode,
                contentColor = if (isSystemInDarkTheme()) greenLight else cian,
                scale = true,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
private fun Item(product: Product, viewModel: MainViewModel, navController: NavController) {
    var isLongPress by remember {
        mutableStateOf(false)
    }
    var detailsIsVisible by remember {
        mutableStateOf(false)
    }
    var deleteIsVisible by remember {
        mutableStateOf(false)
    }
    if (detailsIsVisible) {
        Details(product = product) {
            detailsIsVisible = false
        }
    }
    if (deleteIsVisible) {
        DeleteConfirmation(product = product, viewModel) {
            deleteIsVisible = false
        }
    }
    Card(backgroundColor = if (isSystemInDarkTheme()) black else lightMode,
        elevation = 13.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(top = 10.dp)
            .size(250.dp, 180.dp)
            .combinedClickable(
                onClick = {
                    detailsIsVisible = true
                },
                onLongClick = {
                    if (viewModel.isAdmin) isLongPress = !isLongPress
                }
            )) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        runBlocking {
                            viewModel.getProdImg(product.id_prod)
                        }
                    )
                    .placeholder(R.drawable.ic_upload_img)
                    .crossfade(true)
                    .build(),
                contentDescription = "image_product",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .background(if (isSystemInDarkTheme()) greenLight else cian)
            ) {
                Text(
                    text = product.nombre,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkTheme()) darkMode else lightMode,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 5.dp)
                )
            }
        }
        Box {
            Surface(
                shape = CircleShape,
                color = if (isSystemInDarkTheme()) cian else greenLight,
                modifier = Modifier
                    .height(40.dp)
                    .widthIn(40.dp, 60.dp)
                    .padding(start = 10.dp, top = 8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "${product.cantidad}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp,
                        color = if (isSystemInDarkTheme()) lightMode else darkMode
                    )
                }
            }
        }
        Box {
            AnimatedVisibility(
                visible = isLongPress,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp, top = 8.dp),
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                Column {
                    Button(
                        onClick = {
                            deleteIsVisible = true
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = red
                        ),
                        modifier = Modifier.size(35.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.RemoveCircleOutline,
                            contentDescription = "ic_delete_item",
                            tint = lightMode
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Button(
                        onClick = {
                            viewModel.navigateToUpdate(product.id_prod, navController)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = jade
                        ),
                        modifier = Modifier.size(35.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "ic_edit_item",
                            tint = lightMode
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Details(product: Product, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = if (isSystemInDarkTheme()) darkMode else lightMode
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = product.nombre,
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center,
                    color = if (isSystemInDarkTheme()) greenLight else cian,
                    modifier = Modifier
                        .padding(horizontal = 7.dp)
                )
                Divider(
                    color = if (isSystemInDarkTheme()) greenLight else cian,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Unidades Disponibles",
                    color = if (isSystemInDarkTheme()) lightMode else darkMode
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${product.cantidad}",
                    color = if (isSystemInDarkTheme()) lightMode else darkMode
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Codigo de Barras",
                    color = if (isSystemInDarkTheme()) lightMode else darkMode
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = product.id_prod,
                    color = if (isSystemInDarkTheme()) lightMode else darkMode
                )
            }
        }
    }
}

@Composable
private fun DeleteConfirmation(product: Product, viewModel: MainViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(
                text = "Confirmación",
                fontSize = 20.sp,
                color = if (isSystemInDarkTheme()) greenLight else cian
            )
        },
        text = {
            Text(
                text = "¿Está seguro que desea eliminar el producto?",
                fontSize = 15.sp,
                color = if (isSystemInDarkTheme()) lightMode else darkMode
            )
        },
        backgroundColor = if (isSystemInDarkTheme()) darkMode else lightMode,
        contentColor = if (isSystemInDarkTheme()) lightMode else darkMode,
        confirmButton = {
            Button(
                onClick = {
                    viewModel.deleteProduct(product.id_prod, context)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Unspecified
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(text = "Eliminar", color = red)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Unspecified
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    text = "Cancelar",
                    color = if (isSystemInDarkTheme()) lightMode else darkMode
                )
            }
        },
        shape = RoundedCornerShape(15.dp)
    )
}