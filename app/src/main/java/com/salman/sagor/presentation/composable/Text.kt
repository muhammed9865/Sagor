package com.salman.sagor.presentation.composable

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
@Composable
fun SClickableText(
    modifier: Modifier = Modifier,
    prefixText: String,
    clickableText: String,
    onClick: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        append(text = prefixText)
        withStyle(
            style = MaterialTheme.typography.labelMedium.toSpanStyle()
                .copy(color = MaterialTheme.colorScheme.primary)
        ) {
            append(clickableText)
        }
    }
    ClickableText(modifier = modifier,
        text = annotatedText,
        onClick = {
            onClick()
        })
}