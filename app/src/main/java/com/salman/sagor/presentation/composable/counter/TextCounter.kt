package com.salman.sagor.presentation.composable.counter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.salman.sagor.presentation.composable.AutoSizeText

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/14/2024.
 */
@Composable
fun TextCounter(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    boundaryValues: List<Int>,
) {
    val leftHalfColor = Color(0xff030DF4)
    val rightHalfColor = Color(0xffD91E0B)
    BaseCounter(
        modifier = modifier,
        boundaryValues = boundaryValues,
        leftBoundaryValuesColor = leftHalfColor,
        rightBoundaryValuesColor = rightHalfColor,
    ) {
        TextCounterContent(
            text = text,
            textColor = textColor,
        )
    }
}

@Composable
private fun TextCounterContent(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AutoSizeText(
            text = text,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = textColor,
            ),
            maxLines = 1,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun TextCounterPrev() {
    Box(Modifier.size(200.dp)) {
        TextCounter(
            text = "9489984.18 mg/L", boundaryValues = listOf(0, 100, 200, 400),
            modifier = Modifier.fillMaxSize(),
            textColor = Color(0xFF007813)
        )
    }
}