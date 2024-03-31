package com.salman.sagor.presentation.composable

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.unit.dp
import kotlin.random.Random

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

@Preview(showBackground = true)
@Composable
fun GraphPreview() {
    val points = listOf(
        Pair(0, 0),
        Pair(1, 3),
        Pair(2, 2),
        Pair(3, 4),
        Pair(4, 5),
        Pair(5, 7),
        Pair(6, 21),
        Pair(7, 3),
        Pair(8, 14),
        Pair(9, 7),
        Pair(10, 28),
    )
    val points2 = listOf(
        Pair(0, Random.nextInt(0, 20)),
        Pair(1, Random.nextInt(0, 20)),
        Pair(2, Random.nextInt(0, 20)),
        Pair(3, Random.nextInt(0, 20)),
        Pair(4, Random.nextInt(0, 20)),
        Pair(5, Random.nextInt(0, 20)),
        Pair(6, Random.nextInt(0, 20)),
        Pair(7, Random.nextInt(0, 20)),
        Pair(8, Random.nextInt(0, 20)),
        Pair(9, Random.nextInt(0, 20)),
        Pair(10, Random.nextInt(0, 20)),
        Pair(11, Random.nextInt(0, 20)),
        Pair(12, Random.nextInt(0, 20)),
        Pair(13, Random.nextInt(0, 20)),
        Pair(14, Random.nextInt(0, 20)),
        Pair(15, Random.nextInt(0, 20)),
        Pair(16, Random.nextInt(0, 20)),
        Pair(17, Random.nextInt(0, 20)),
        Pair(18, Random.nextInt(0, 20)),
    )
    val points3 = listOf(
        Pair(0, Random.nextInt(0, 20)),
        Pair(1, Random.nextInt(0, 20)),
        Pair(2, Random.nextInt(0, 20)),
        Pair(3, Random.nextInt(0, 20)),
        Pair(4, Random.nextInt(0, 20)),
        Pair(5, Random.nextInt(0, 20)),
        Pair(6, Random.nextInt(0, 20)),
        Pair(7, Random.nextInt(0, 20)),
        Pair(8, Random.nextInt(0, 20)),
        Pair(9, Random.nextInt(0, 20)),
        Pair(10, Random.nextInt(0, 20)),
    )
    val graphPoints = listOf(
        GraphPoints(points, Color(0xff3BB74F)),
        GraphPoints(points2, Color(0xffCD3131)),
        GraphPoints(points3, Color(0xff3B74B7)),
    )
    Graph(graphPoints = graphPoints)
}