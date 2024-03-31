package com.salman.sagor.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.salman.sagor.presentation.composable.Graph
import com.salman.sagor.presentation.composable.GraphPoints
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    val graphPoints = remember {
        mutableStateListOf<GraphPoints>()
    }

    scope.launch {
        while (true) {
            graphPoints.clear()
            val p1 = getRandomPoints(10).run { GraphPoints(this, Color(0xFF023E8A)) }
            val p2 = getRandomPoints(10).run { GraphPoints(this, Color(0xFFCD3131)) }
            val p3 = getRandomPoints(10).run { GraphPoints(this, Color(0xFF3BB74F)) }
            graphPoints.addAll(listOf(p1, p2, p3))
            animateGraphPoints(graphPoints, listOf(p1, p2, p3))
            delay(2000L)
        }
    }

    Graph(
        graphPoints = graphPoints,
        graphPadding = 30
    )
}

fun getRandomPoints(size: Int): List<Pair<Int, Int>> {
    val points = mutableListOf<Pair<Int, Int>>()
    repeat(size) {
        points.add(Pair(it, (0..10).random()))
    }
    return points
}


suspend fun animateGraphPoints(currentPoints: MutableList<GraphPoints>, newPoints: List<GraphPoints>) {
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
            val newX = currentPoint.first + (newGraphPoints.points[pointIndex].first - currentPoint.first) * progress
            val newY = currentPoint.second + (newGraphPoints.points[pointIndex].second - currentPoint.second) * progress
            newX.toInt() to newY.toInt()
        }
        GraphPoints(interpolatedPoints, currentGraphPoints.color)
    }
}