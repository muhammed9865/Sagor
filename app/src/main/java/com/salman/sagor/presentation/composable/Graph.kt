package com.salman.sagor.presentation.composable

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.salman.sagor.presentation.core.animateListOfFloats
import com.salman.sagor.presentation.core.color
import com.salman.sagor.presentation.model.GraphValues

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/31/2024.
 */

@Composable
fun Graph(
    modifier: Modifier = Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    values: List<GraphValues>,
    padding: Dp = 5.dp,
) {

    val valuesAnimated = values.map {
        animateListOfFloats(targetFractions = it.history)
    }
    Canvas(
        modifier = modifier
            .padding(padding)
    ) {
        val xAxisStepSize = (size.width) / xValues.size
        values.forEachIndexed { index, graphValues ->
            drawPointsLine(
                points = valuesAnimated[index].slices,
                xValues = xValues,
                yValues = yValues,
                xAxisStepSize = xAxisStepSize,
                color = graphValues.color,
                drawCircles = false
            )
        }
    }
}

private fun DrawScope.drawPointsLine(
    points: List<State<Float>>,
    xValues: List<Int>,
    yValues: List<Int>,
    xAxisStepSize: Float,
    color: Color,
    drawCircles: Boolean = false,
) {
    if (points.isEmpty()) return

    val strokeWidth = size.minDimension * 0.02f
    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
    val height = size.height

    for (i in points.indices) {
        val x1 = if (i == 0) 0f else xAxisStepSize * xValues[i]
        // ensure the points are within the bounds of the graph
        val y1 = maxOf(
            0f,
            minOf(height, height - (points[i].value * height) / yValues.maxOrNull()!!)
        )
        coordinates.add(PointF(x1, y1))
        /** drawing circles to indicate all the points */
        if (drawCircles)
            drawCircle(
                color = Color.Red,
                radius = 10f,
                center = Offset(x1, y1)
            )

    }

    // Used for drawing the Bezier curve
    for (i in 1 until coordinates.size) {
        val c1 = PointF(
            (coordinates[i].x + coordinates[i - 1].x) / 2,
            coordinates[i - 1].y
        )
        val c2 = PointF(
            (coordinates[i].x + coordinates[i - 1].x) / 2,
            coordinates[i].y
        )
        controlPoints1.add(c1)
        controlPoints2.add(c2)
    }

    val stroke = Path().apply {
        reset()
        if (controlPoints1.isNotEmpty() && controlPoints2.isNotEmpty()) {
            moveTo(coordinates.first().x, coordinates.first().y)
            for (i in 0 until coordinates.size - 1) {
                cubicTo(
                    controlPoints1[i].x, controlPoints1[i].y,
                    controlPoints2[i].x, controlPoints2[i].y,
                    coordinates[i + 1].x, coordinates[i + 1].y
                )
            }
        }
    }

    drawPath(
        stroke,
        color = color,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}

fun randomColor(): Color {
    return Color(
        red = (0..255).random() / 255f,
        green = (0..255).random() / 255f,
        blue = (0..255).random() / 255f,
        alpha = 1f
    )
}