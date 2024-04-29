package com.salman.sagor.presentation.composable

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/29/2024.
 */

@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    LaunchedEffect(message) {
        if (message.isNotBlank())
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}