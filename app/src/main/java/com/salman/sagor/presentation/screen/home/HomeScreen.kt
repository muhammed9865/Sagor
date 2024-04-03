package com.salman.sagor.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.salman.sagor.presentation.composable.GraphSecond
import kotlin.random.Random

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */

@Composable
fun HomeScreen(
    viewModel: GraphViewModel = hiltViewModel()
) {

    val points by viewModel.lines.collectAsState()

    Box(Modifier.padding(16.dp)) {
        GraphSecond(
            xValues = viewModel.xValues,
            yValues = viewModel.yValues,
            values = points,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

        )
    }
}

fun getRandomPoints(size: Int): List<Float> {
    return List(size) {
        Random.nextDouble(0.0, 50.0).toFloat()
    }
}
