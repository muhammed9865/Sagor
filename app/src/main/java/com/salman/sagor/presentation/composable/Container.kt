package com.salman.sagor.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 6/27/2024.
 */
@Composable
fun BorderedSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier
            .fillMaxWidth()
            .aspectRatio(1.7777f),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.small
    ) {
        content()
    }

}