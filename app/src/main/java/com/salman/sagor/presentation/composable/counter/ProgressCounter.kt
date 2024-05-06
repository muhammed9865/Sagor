package com.salman.sagor.presentation.composable.counter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import com.salman.sagor.domain.model.MetricValueType
import com.salman.sagor.domain.model.PoolMetric
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/7/2024.
 */
@Composable
fun ProgressCounter(
    modifier: Modifier = Modifier,
    metric: PoolMetric
) {
    val red = Color(0xFFD91E0B)
    val green = Color(0xFF3BB74F)
    Box(modifier = modifier.defaultMinSize(200.dp, 200.dp)) {
        BaseCounter(
            rightBoundaryValuesColor = red,
            leftBoundaryValuesColor = green,
            boundaryValues = metric.boundaryValues,
            name = metric.name,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawProgressArc(listOf(green, red))
                    drawIndicator(minOf(metric.value, metric.maxValue), metric.maxValue)
                    drawProgressText(metric.value)
                }
            }
        }
    }
}

private fun DrawScope.drawProgressArc(colors: List<Color>) {
    val gradientBrush = Brush.linearGradient(
        colors = colors,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f)
    )

    val strokeWidth = size.width * 0.14f

    // Draw the arc with the gradient brush
    drawArc(
        brush = gradientBrush,
        style = Stroke(strokeWidth),
        startAngle = 125f,
        sweepAngle = 290f,
        useCenter = false
    )
}

private fun DrawScope.drawIndicator(progress: Float, maxValue: Float = 100f) {
    val color = Color(0xFFD9D9D9)
    val radius = this.size.width * 0.07f
    val rectSize = Size(15f, -size.height * 0.12f)
    val progressFraction = progress / maxValue
    val progressAngle = (125 + progressFraction * 290) - 90
    val rectTopLeft = Offset(center.x - 8f, center.y)
    val circleCenter = center

    drawIntoCanvas { canvas ->
        canvas.nativeCanvas.withRotation(
            degrees = progressAngle,
            pivotX = circleCenter.x,
            pivotY = circleCenter.y
        ) {
            // Calculate the end position of the indicator stick based on the progress angle
            val startOffset = Offset(circleCenter.x, circleCenter.y)
            val endX = size.width / 2
            val endY = size.height * 0.9f
            val endOffset = Offset(endX, endY)

            val lineColor = Color(0xFFDF8E89)
            drawLine(lineColor, startOffset, endOffset, 2f)
            drawRect(color, rectTopLeft, size = rectSize)
        }
    }
    drawCircle(color, radius, circleCenter)
}

private fun DrawScope.drawProgressText(progress: Float) {
    val textColor = Color(0xFFCD3131)
    val fontSize = this.size.width / 7
    val paint = Paint().apply {
        color = textColor
    }.asFrameworkPaint().apply {
        textSize = fontSize
    }

    val text = progress.toString().format("%.2f")

    val measuredWidth = paint.measureText(text)
    val measuredHeight = paint.fontMetrics.bottom - paint.fontMetrics.top
    // make the text to be centered

    drawIntoCanvas {
        it.nativeCanvas.drawText(
            text,
            (size.width - measuredWidth).div(2),
            (size.height - measuredHeight).div(2.5f),
            paint
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressCounterPrev() {
    val maxVal = 200f
    val random = remember {
        RandomFloat(maxVal)
    }
    val progress = random.progress.collectAsState()
    val progressAnimated by animateFloatAsState(
        targetValue = progress.value, label = "",
        animationSpec = tween(500)
    )
    // normalize the maxValue and the progress

    LaunchedEffect(Unit) {
        while (isActive) {
            random.work()
        }
    }
    // create boundary values from 0 to max value and each value ia a step of 50
    val metric = PoolMetric(
        "Oxygen",
        progressAnimated,
        maxValue = 200f,
        boundaryValues = listOf(0, 75, 125, 200),
        MetricValueType.Progress,
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ProgressCounter(
            metric = metric,
            modifier = Modifier.size(200.dp)
        )
    }
}


class RandomFloat(private val maxValue: Float) {
    var progress = MutableStateFlow(0f)
    private var isReverse = false
    suspend fun work() {
        if (progress.value + 1 >= maxValue && !isReverse) {
            isReverse = true
            delay(1000)
        }
        if (progress.value - 1 <= 0f && isReverse) {
            isReverse = false
            delay(1000)
        }

        progress.value += if (isReverse) -10f else 10f
        delay(200)
    }
}