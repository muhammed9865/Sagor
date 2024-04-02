package com.salman.sagor.presentation.composable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/31/2024.
 */
data class GraphPoints(
    val points: List<Pair<Int, Int>>,
    val color: Color,
)

@Composable
fun Graph(
    modifier: Modifier = Modifier,
    graphPoints: List<GraphPoints>,
    xAxisRange: IntRange = 0..5,
    yAxisRange: IntRange = 0..2,
    graphPadding: Int = 30,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val width = size.width
        val height = size.height


        drawIntoCanvas { canvas ->
            // Draw x-axis values
            xAxisRange.forEach { i ->
                val xPos = (i.toFloat() / xAxisRange.last.toFloat()) * width
                canvas.nativeCanvas.drawText(
                    i.toString(),
                    xPos,
                    height,
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 16f
                    }
                )
            }

            // Draw y-axis values
            for (i in yAxisRange) {
                val xPos = 0f
                val yPos = height - (i.toFloat() / yAxisRange.last.toFloat()) * height
                canvas.nativeCanvas.drawText(
                    i.toString(),
                    xPos,
                    yPos,
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 16f
                    }
                )
            }
        }


        graphPoints.forEachIndexed { index, graphPoints ->
            val path = Path()
            graphPoints.points.forEach { point ->
                val x = point.first.toFloat()
                val y = point.second.toFloat()
                val canvasPoint = Offset(
                    x = graphPadding + (x / graphPoints.points.size) * width,
                    y = height - graphPadding - (y / graphPoints.points.size) * height
                )

                if (index == 0) {
                    path.moveTo(canvasPoint.x, canvasPoint.y)
                } else {
                    path.quadraticTo(
                        (canvasPoint.x + path.getBounds().width) / 2,
                        (canvasPoint.y + path.getBounds().size.height) / 2,
                        canvasPoint.x,
                        canvasPoint.y
                    )
                }
            }
            drawPath(
                path = path,
                color = graphPoints.color,
                style = Stroke(
                    width = 5f, cap = StrokeCap.Round, miter = 900f,
                    join = StrokeJoin.Round
                ),
            )
        }
    }

}

@Composable
fun GraphSecond(
    modifier: Modifier = Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    padding: Dp = 30.dp,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
            .shadow(2.dp)

    ) {
        val textColor = MaterialTheme.colorScheme.onBackground.toArgb()
        Canvas(modifier = Modifier.fillMaxSize()) {
            val nativeCanvas = drawContext.canvas.nativeCanvas
            val xAxisStepSize = (size.width - padding.toPx()) / xValues.size
            val yAxisStepSize = size.height / (yValues.size + 1)
            val textPaint = Paint().apply {
                color = textColor
                textSize = 40f
            }

            println("Size: w${size.width} h${size.height}")

            // These are used for drawing the Bezier curve
            val controlPoints1 = mutableListOf<PointF>()
            val controlPoints2 = mutableListOf<PointF>()
            val coordinates = mutableListOf<PointF>()

            xValues.forEachIndexed { index, i ->
                nativeCanvas.drawText(
                    i.toString(),
                    xAxisStepSize * (index + 1),
                    size.height - 20,
                    textPaint
                )
            }

            yValues.forEachIndexed { index, i ->
                nativeCanvas.drawText(
                    i.toString(),
                    padding.toPx() / 4f,
                    size.height - (yAxisStepSize * (index + 1)),
                    textPaint

                )
            }

            for (i in points.indices) {
                val x1 = xAxisStepSize  * xValues[i] + 10
                val y1 = size.height - (yAxisStepSize * ((points[i]) ))
                coordinates.add(PointF(x1,y1))
                /** drawing circles to indicate all the points */
                drawCircle(
                    color = Color.Red,
                    radius = 10f,
                    center = Offset(x1,y1)
                )
            }

            // Used for drawing the Bezier curve
            for (i in 1 until coordinates.size) {
                val c1 = PointF(
                    (coordinates[i].x + coordinates[i-1].x) / 2,
                    coordinates[i - 1].y
                )
                val c2 = PointF(
                    (coordinates[i].x + coordinates[i-1].x) / 2,
                    coordinates[i].y
                )
                controlPoints1.add(c1)
                controlPoints2.add(c2)
            }

            val stroke = Path().apply {
                reset()
                if (controlPoints1.isNotEmpty() && controlPoints2.isNotEmpty()) {
                    moveTo(coordinates.first().x, coordinates.first().y)
                    for (i in 0 until  coordinates.size - 1) {
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
                color = Color.Black,
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GraphPreview() {
    val points = listOf(1.5f, 2.4f, 3.6f, 4.5f, 2f, 5.3f)
    val xValues = listOf(1, 2, 3, 4, 5, 6, 7)
    val yValues = listOf(1, 2, 3, 4, 5, 6, 7, 9, 10, 12, 14, 15)

    GraphSecond(
        xValues = xValues, yValues = yValues, points = points,
        modifier = Modifier.fillMaxWidth().height(500.dp)
    )
}