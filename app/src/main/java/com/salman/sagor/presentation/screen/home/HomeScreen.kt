package com.salman.sagor.presentation.screen.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.salman.sagor.presentation.composable.Graph
import com.salman.sagor.presentation.composable.counter.ProgressCounter
import com.salman.sagor.presentation.composable.counter.TextCounter
import kotlin.random.Random

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */

@Composable
fun HomeScreen(
    viewModel: GraphViewModel = hiltViewModel()
) {

    val points by viewModel.lines.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val progressAnimated by animateFloatAsState(
        targetValue = progress, label = "",
        animationSpec = tween(500)
    )

    Column(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Graph(
            xValues = viewModel.xValues,
            yValues = viewModel.yValues,
            values = points,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)

        )

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                shape = MaterialTheme.shapes.extraSmall,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 5.dp
            ) {
                TextCounter(
                    text = "$progressAnimated mg/L",
                    textColor = Color(0xFF023E8A),
                    boundaryValues = listOf(0, 75, 125, 200),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                shape = MaterialTheme.shapes.extraSmall,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 5.dp
            ) {
                ProgressCounter(
                    progress = progressAnimated,
                    maxValue = 200f,
                    boundaryValues = listOf(0, 75, 125, 200),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                shape = MaterialTheme.shapes.extraSmall,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 5.dp
            ) {
                TextCounter(
                    text = "$progressAnimated mg/L",
                    textColor = Color(0xFF007813),
                    boundaryValues = listOf(0, 75, 125, 200),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
private fun CounterWrapper(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        shadowElevation = 5.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        content()
    }
}

fun getRandomPoints(size: Int): List<Float> {
    return List(size) {
        Random.nextDouble(0.0, 50.0).toFloat()
    }
}
