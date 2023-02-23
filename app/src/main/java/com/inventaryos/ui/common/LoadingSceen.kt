package com.inventaryos.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.inventaryos.ui.theme.cian
import com.inventaryos.ui.theme.darkMode
import com.inventaryos.ui.theme.greenLight
import com.inventaryos.ui.theme.lightMode

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun Preview() {
    LoadingScreen(true)
}

@Composable
fun LoadingScreen(isLoading: Boolean) {
    if (isLoading) {
        Dialog(onDismissRequest = {}) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSystemInDarkTheme()) darkMode else lightMode,
                modifier = Modifier
                    .size(100.dp)
            ) {
                CircularProgressIndicator(
                    color = if (isSystemInDarkTheme()) greenLight else cian,
                    modifier = Modifier
                        .padding(20.dp)
                )
            }
        }
    }
}