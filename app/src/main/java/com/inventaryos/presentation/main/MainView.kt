package com.inventaryos.presentation.main

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inventaryos.R
import com.inventaryos.ui.theme.*

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun Preview() {
    MainView()
}

@Composable
fun MainView() {
    Scaffold(
        topBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
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
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "ic_search",
                        modifier = Modifier
                            .size(35.dp),
                        tint = if (isSystemInDarkTheme()) lightMode else darkMode
                    )
                }
            }
        },
        backgroundColor = if (isSystemInDarkTheme()) darkMode else lightMode
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            items(10) {
                Item()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Item() {
    var isLongPress by remember {
        mutableStateOf(false)
    }
    Card(
        backgroundColor = if (isSystemInDarkTheme()) black else lightMode,
        elevation = 13.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(top = 10.dp)
            .size(250.dp, 180.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        isLongPress = !isLongPress
                    }
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.example),
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
                    text = "Camiseta",
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
                        text = "1",
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
                Button(
                    onClick = { /*TODO*/ },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = red
                    ),
                    modifier = Modifier
                        .size(35.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.RemoveCircleOutline,
                        contentDescription = "ic_delete_item",
                        tint = lightMode
                    )
                }
            }
        }
    }
}