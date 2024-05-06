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
import com.salman.sagor.domain.model.GraphValues
import com.salman.sagor.presentation.core.animateListOfFloats
import com.salman.sagor.presentation.core.color

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/31/2024.
 */

private const val DEFAULT_STEPS = 5

@Composable
fun Graph(
    modifier: Modifier = Modifier,
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
        val xAxisStepSize =
            (size.width) / (values.map { it.history }.maxOfOrNull { it.size } ?: DEFAULT_STEPS)
        // normalize values to be within the bounds of the graph
        values.forEachIndexed { index, graphValues ->
            drawPointsLine(
                points = valuesAnimated[index].slices,
                xAxisStepSize = xAxisStepSize,
                color = graphValues.color,
                drawCircles = false
            )
        }
    }
}

private fun DrawScope.drawPointsLine(
    points: List<State<Float>>,
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
        // normalize the x value to be within the bounds of the graph
        val x1 = maxOf(
            0f,
            minOf(size.width, xAxisStepSize * i)
        )
        // ensure the points are within the bounds of the graph
        val y1 = maxOf(
            0f,
            minOf(height, height - (points[i].value * height))
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

    /** Used for drawing the Bezier curve
    Getting two control points to control the curve
    First one is in center between current point and previous one and in the same height
    as the previous point
    Second one is in center between current point and previous one and in the same height
    as the current point
     **/
    for (i in 1 until coordinates.size) {
        val previousPointControl = PointF(
            (coordinates[i].x + coordinates[i - 1].x) / 2,
            coordinates[i - 1].y
        )
        val currentPointControl = PointF(
            (coordinates[i].x + coordinates[i - 1].x) / 2,
            coordinates[i].y
        )
        controlPoints1.add(previousPointControl)
        controlPoints2.add(currentPointControl)
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