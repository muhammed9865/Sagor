package com.salman.sagor.presentation.screen.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.salman.sagor.presentation.composable.Graph
import com.salman.sagor.presentation.composable.Screen
import com.salman.sagor.presentation.composable.counter.ProgressCounter
import com.salman.sagor.presentation.composable.counter.TextCounter

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/19/2024.
 */
@Preview(showBackground = true)
@Composable
private fun HomeScreenDemo() {
    val viewModel = remember {
        HomeViewModel()
    }
    val pools by viewModel.pools.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val progressAnimated by animateFloatAsState(
        targetValue = progress, label = "",
        animationSpec = tween(500)
    )
    Screen(title = "Home") {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Graph(
                xValues = pools[0].xValues,
                yValues = pools[0].yValues,
                values = pools[0].values,
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
}