package com.salman.sagor.presentation.composable.counter

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/7/2024.
 */

@Composable
fun BaseCounter(
    modifier: Modifier = Modifier,
    leftBoundaryValuesColor: Color,
    rightBoundaryValuesColor: Color,
    leftLinesColor: Color = Color(0xFF023E8A),
    rightLinesColor: Color = Color(0xFF8D1414),
    boundaryValues: List<Int>,
    name: String? = null,
    nameColor: Color? = null,
    content: @Composable () -> Unit
) {
    val minSize = 100.dp
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val linesMargin = 0
    val contentPaddingPercentage = 0.12f
    Box(
        modifier = modifier
            .sizeIn(minWidth = minSize, minHeight = minSize)
            .padding(5.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    size = it.size
                }
        ) {
            val radius = (this.size.width - 20) / 2f
            drawLines(
                radius,
                linesMargin,
                leftLinesColor,
                rightLinesColor
            )
            drawBoundaryValues(
                radius, values = boundaryValues,
                leftBoundaryValuesColor,
                rightBoundaryValuesColor
            )
            name?.let {
                val color = nameColor ?: leftLinesColor
                drawName(it, color)
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    horizontal = (size.width * contentPaddingPercentage)
                        .toInt()
                        .pxToDp(),
                    vertical = (size.height * contentPaddingPercentage)
                        .toInt()
                        .pxToDp()
                )
        ) {
            content()
        }
    }
}

private fun DrawScope.drawLines(
    radius: Float, lineMargin: Int = -10,
    leftLinesColor: Color,
    rightLinesColor: Color,
) {
    val linesCount = 10
    var degree = 90.toDouble()
    val stepSize = 360 / linesCount
    val lineHeight = size.maxDimension * 0.08f
    val lineStrokeWidth = size.maxDimension * 0.015f
    for (i in 0..linesCount) {
        val color = if (i <= linesCount / 2) {
            leftLinesColor
        } else rightLinesColor

        val sX =
            (center.x + (radius + lineMargin) * cos(Math.toRadians(degree)).toFloat())
        val sY =
            (center.y + (radius + lineMargin) * sin(Math.toRadians(degree)).toFloat())
        val startOffset = Offset(sX, sY)
        val eX = (startOffset.x + lineHeight * cos(Math.toRadians(degree)).toFloat())
        val eY = (startOffset.y + lineHeight * sin(Math.toRadians(degree)).toFloat())
        val endOffset = Offset(eX, eY)
        if (i % linesCount != 0) // skip the last line at the bottom
            drawLine(color, startOffset, endOffset, lineStrokeWidth)
        degree += stepSize % 360
    }
}

private fun DrawScope.drawBoundaryValues(
    radius: Float, values: List<Int>,
    leftValuesColor: Color,
    rightValuesColor: Color,
) {
    val canvas = drawContext.canvas.nativeCanvas
    val textSize = size.minDimension * 0.1f
    val leftPaint = Paint().apply {
        color = leftValuesColor
    }.asFrameworkPaint().apply {
        this.textSize = textSize
    }
    val rightPaint = Paint().apply {
        color = rightValuesColor
    }.asFrameworkPaint().apply {
        this.textSize = textSize
    }

    var degree = 135.0 // initially 135 as a start.
    val stepSize = 360 / maxOf(values.size, 4)
    val valueMargin = 30

    repeat(values.size) {
        val paint = if (it < values.size / 2) { // paint selection for left/right values' color
            leftPaint
        } else rightPaint

        val textWidth = paint.measureText(values[it].toString())
        val textHeight = paint.fontMetrics.bottom - paint.fontMetrics.top

        val sX =
            (center.x + (radius + (valueMargin + 0.5f * it)) * cos(Math.toRadians(degree)).toFloat() - textWidth / 2) // Adjust x-coordinate to center the text horizontally
        val sY =
            (center.y + (radius + (valueMargin + 0.2f * it)) * sin(Math.toRadians(degree)).toFloat() + textHeight / 2) // Adjust y-coordinate to align the text vertically


        canvas.drawText(
            values[it].toString(),
            sX,
            sY,
            paint,
        )
        degree += stepSize % 360
    }
}

private fun DrawScope.drawName(name: String, color: Color) {
    val textSize = size.minDimension * 0.1f
    val paint = Paint().apply {
        this.color = color
    }.asFrameworkPaint().apply {
        this.textSize = textSize
    }
    val textMeasuredSize = paint.measureText(name)
    val topLeft = Offset(
        x = (size.width - textMeasuredSize) / 2f,
        y = size.height + 10
    )

    val canvas = drawContext.canvas.nativeCanvas
    canvas.drawText(
        name,
        topLeft.x,
        topLeft.y,
        paint
    )
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) {
    toDp()
}

@Preview(showBackground = true)
@Composable
private fun CircleCounterPrev() {
    val red = Color(0xFFD91E0B)
    val green = Color(0xFF3BB74F)
    Box(Modifier.height(100.dp)) {
        BaseCounter(
            rightBoundaryValuesColor = red,
            leftBoundaryValuesColor = green,
            boundaryValues = listOf(0, 100, 200, 300),
            name = "Temperature",
            modifier = Modifier.size(100.dp)
        ) {

        }
    }
}