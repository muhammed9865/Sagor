package com.salman.sagor.presentation.composable

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun AutoSizeText(
    text: String,
    textStyle: TextStyle,
    maxLines: Int,
    modifier: Modifier = Modifier
) {
    var scaledTextStyle by remember { mutableStateOf(textStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text,
        modifier.drawWithContent {
            if (readyToDraw) {
                drawContent()
            }
        },
        style = scaledTextStyle,
        softWrap = false,
        maxLines = maxLines,
        fontWeight = FontWeight.Bold,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                scaledTextStyle =
                    scaledTextStyle.copy(fontSize = scaledTextStyle.fontSize * 0.98)
            } else {
                readyToDraw = true
            }
        }
    )
}