package com.salman.sagor.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salman.sagor.presentation.composable.GraphPoints
import com.salman.sagor.presentation.composable.GraphSecond
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.random.Random

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    val graphPoints = remember {
        mutableStateListOf<Float>()
    }

    scope.launch {
        while (true) {
            graphPoints.clear()
            graphPoints.addAll(getRandomPoints(5))
            println(graphPoints)
            delay(2000L)
        }
    }

    GraphSecond(
        xValues = listOf(1, 2, 3, 4, 5), yValues = listOf(1, 3, 5, 7, 9, 10), points = graphPoints,
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}

fun getRandomPoints(size: Int): List<Float> {
    return List(size) {
        Random.nextDouble(0.0, 10.0).toFloat()
    }
}


suspend fun animateGraphPoints(
    currentPoints: MutableList<GraphPoints>,
    newPoints: List<GraphPoints>
) {
    val startTime = System.currentTimeMillis()
    val duration = 1000L // Animation duration in milliseconds

    // Interpolate between the current and new points over time
    while (System.currentTimeMillis() - startTime < duration) {
        val progress = (System.currentTimeMillis() - startTime).toFloat() / duration
        val interpolatedPoints = interpolateGraphPoints(currentPoints, newPoints, progress)
        currentPoints.clear()
        currentPoints.addAll(interpolatedPoints)
        delay(16L) // Adjust the delay for smoother animation (60 frames per second)
    }
    // Ensure final state matches the newPoints
    currentPoints.clear()
    currentPoints.addAll(newPoints)
}

fun interpolateGraphPoints(
    currentPoints: List<GraphPoints>,
    newPoints: List<GraphPoints>,
    progress: Float
): List<GraphPoints> {
    // Interpolate each point pair between currentPoints and newPoints
    return currentPoints.mapIndexed { index, currentGraphPoints ->
        val newGraphPoints = newPoints[index]
        val interpolatedPoints = currentGraphPoints.points.mapIndexed { pointIndex, currentPoint ->
            val newX =
                currentPoint.first + (newGraphPoints.points[pointIndex].first - currentPoint.first) * progress
            val newY =
                currentPoint.second + (newGraphPoints.points[pointIndex].second - currentPoint.second) * progress
            newX.toInt() to newY.toInt()
        }
        GraphPoints(interpolatedPoints, currentGraphPoints.color)
    }
}