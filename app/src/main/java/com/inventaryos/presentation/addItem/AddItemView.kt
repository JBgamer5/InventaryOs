package com.inventaryos.presentation.addItem

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.inventaryos.MainActivity
import com.inventaryos.R
import com.inventaryos.ui.theme.*
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun Preview() {
    AddItemView()
}

var tittle by mutableStateOf("")
var imageUri by mutableStateOf("")
var barCode by mutableStateOf("")
var quantity by mutableStateOf(0)

@Composable
fun AddItemView() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = if (isSystemInDarkTheme()) greenLight else cian,
                modifier = Modifier
                    .size(70.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "ic_save",
                    tint = if (isSystemInDarkTheme()) darkMode else lightMode,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }) {
        Content(it)
    }
}

@Composable
private fun Content(paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) darkMode else lightMode)
            .padding(paddingValues)
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Añadir Producto",
            fontSize = 45.sp,
            fontFamily = Lobster,
            color = if (isSystemInDarkTheme()) greenLight else cian
        )
        Spacer(modifier = Modifier.height(10.dp))
        ItemPreview()
        Spacer(modifier = Modifier.height(40.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Surface(
                    color = Color.Unspecified,
                    elevation = 7.dp,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 15.dp)
                ) {
                    TextField(
                        value = tittle,
                        onValueChange = { tittle = it },
                        placeholder = {
                            Text(text = "Escriba el nombre")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = if (isSystemInDarkTheme()) black else lightMode,
                            placeholderColor = if (isSystemInDarkTheme()) lightMode.copy(.6f) else darkMode.copy(
                                .6f
                            ),
                            textColor = if (isSystemInDarkTheme()) lightMode else darkMode,
                            cursorColor = if (isSystemInDarkTheme()) greenLight else cian,
                            focusedIndicatorColor = if (isSystemInDarkTheme()) greenLight else cian,
                            unfocusedIndicatorColor = Color.Unspecified,
                        )
                    )
                }
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .width(230.dp)
                ) {
                    Button(
                        onClick = { if (quantity > 0) quantity = quantity.minus(1) },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = red
                        ),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 7.dp,
                            pressedElevation = 9.dp
                        ),
                        modifier = Modifier
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "ic_remove_quantity",
                            tint = if (isSystemInDarkTheme()) darkMode else lightMode
                        )
                    }
                    Surface(
                        color = Color.Unspecified,
                        elevation = 7.dp,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 15.dp)
                    ) {
                        TextField(
                            value = "$quantity",
                            onValueChange = {
                                if (it.isDigitsOnly() && it.isNotEmpty() && it.length < 9) quantity =
                                    it.toInt() else if (it.isEmpty()) quantity = 0
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = if (isSystemInDarkTheme()) black else lightMode,
                                unfocusedLabelColor = if (isSystemInDarkTheme()) lightMode.copy(.6f) else darkMode.copy(
                                    .6f
                                ),
                                focusedIndicatorColor = if (isSystemInDarkTheme()) greenLight else cian,
                                unfocusedIndicatorColor = Color.Unspecified,
                                focusedLabelColor = if (isSystemInDarkTheme()) greenLight else cian,
                                textColor = if (isSystemInDarkTheme()) lightMode else darkMode,
                                cursorColor = if (isSystemInDarkTheme()) greenLight else cian
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = {
                                Text(
                                    text = "Cantidad",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            },
                            textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center),
                            modifier = Modifier
                                .width(100.dp)
                        )
                    }
                    Button(
                        onClick = { quantity = quantity.plus(1) },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = jade
                        ),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 7.dp,
                            pressedElevation = 9.dp
                        ),
                        modifier = Modifier
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "ic_add_quantity",
                            tint = if (isSystemInDarkTheme()) darkMode else lightMode
                        )
                    }
                }
            }

            item {
                val pickImage = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        if (uri != null) {
                            imageUri = uri.toString()
                        }
                    }
                )
                Button(
                    onClick = { pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isSystemInDarkTheme()) greenLight else cian
                    ),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 7.dp,
                        pressedElevation = 9.dp
                    ),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Cargar Imagen",
                        color = if (isSystemInDarkTheme()) darkMode else lightMode
                    )
                }
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        color = Color.Unspecified,
                        elevation = 7.dp,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 15.dp)
                    ) {
                        TextField(
                            value = barCode,
                            onValueChange = {},
                            placeholder = {
                                Text(text = "Presione el boton de escanear")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = if (isSystemInDarkTheme()) black else lightMode,
                                placeholderColor = if (isSystemInDarkTheme()) lightMode.copy(.6f) else darkMode.copy(
                                    .6f
                                ),
                                textColor = if (isSystemInDarkTheme()) lightMode else darkMode,
                                cursorColor = if (isSystemInDarkTheme()) greenLight else cian,
                                focusedIndicatorColor = if (isSystemInDarkTheme()) greenLight else cian,
                                unfocusedIndicatorColor = Color.Unspecified,
                            ),
                            readOnly = true
                        )
                    }
                    val scanLauncher =
                        rememberLauncherForActivityResult(
                            contract = ScanContract(),
                            onResult = { result ->
                                if (!result.contents.isNullOrEmpty()) {
                                    barCode = result.contents
                                }
                            })
                    Button(
                        onClick = {
                            scanLauncher.launch(
                                ScanOptions().setPrompt("Acerque el codigo de barras")
                                    .setDesiredBarcodeFormats(ScanOptions.PRODUCT_CODE_TYPES)
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isSystemInDarkTheme()) greenLight else cian
                        ),
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 7.dp,
                            pressedElevation = 9.dp
                        ),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "Escanear Codigo de Barras",
                            color = if (isSystemInDarkTheme()) darkMode else lightMode
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemPreview() {
    Card(
        backgroundColor = if (isSystemInDarkTheme()) black else lightMode,
        elevation = 13.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(top = 10.dp)
            .size(250.dp, 180.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(if (imageUri.isBlank()) R.drawable.ic_upload_img else imageUri)
                    .placeholder(R.drawable.ic_upload_img)
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
                    text = if (tittle.isBlank()) "Sin nombre" else tittle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkTheme()) darkMode else lightMode,
                    modifier = Modifier
                        .align(Alignment.Center)
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
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "$quantity",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp,
                        color = if (isSystemInDarkTheme()) lightMode else darkMode
                    )
                }
            }
        }
    }
}