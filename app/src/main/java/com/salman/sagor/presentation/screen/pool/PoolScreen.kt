package com.salman.sagor.presentation.screen.pool

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.salman.sagor.R
import com.salman.sagor.domain.model.MetricValueType
import com.salman.sagor.domain.model.Pool
import com.salman.sagor.domain.model.PoolMetric
import com.salman.sagor.presentation.composable.Graph
import com.salman.sagor.presentation.composable.Screen
import com.salman.sagor.presentation.composable.counter.ProgressCounter
import com.salman.sagor.presentation.composable.counter.TextCounter
import com.salman.sagor.presentation.navigation.LocalNavigator
import com.salman.sagor.presentation.screen.home.HomeViewModel

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
@Composable
fun PoolScreen(
    poolId: Int,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val navigator = LocalNavigator.current
    if (poolId == -1) navigator.popBackStack()

    val pool = homeViewModel.pools.collectAsState().run {
        value.first { it.id == poolId }
    }

    Screen(
        onBackPressed = navigator::popBackStack,
        title = pool.name,
        actions = {
            IconButton(onClick = { /*TODO Edit Name*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit name"
                )
            }
        }
    ) {
        PoolContent(pool)
    }
}

@Composable
private fun PoolContent(pool: Pool) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        PoolGraph(pool)
        MetricsSection(pool.metrics)
    }
}

@Composable
private fun PoolGraph(pool: Pool, modifier: Modifier = Modifier) {
    val namesAndColors = pool.values.map { it.name to it.color }
    Column(
        modifier
            .fillMaxWidth()
            .aspectRatio(1 / 4f)
    ) {
        Graph(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            xValues = pool.xValues,
            yValues = pool.yValues,
            values = pool.values,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            namesAndColors.forEach { (name, color) ->
                GraphLegend(name = name, color = color)
            }
        }
    }
}

@Composable
private fun MetricsSection(metrics: List<PoolMetric>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.readings),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(text = "Last updated 2 minutes ago", style = MaterialTheme.typography.bodySmall)

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            metrics.forEach { metric ->
                PoolMetric(metric)
            }
        }
    }
}

@Composable
private fun RowScope.PoolMetric(metric: PoolMetric) {
    val progressAnimated by animateFloatAsState(
        targetValue = metric.value, label = "",
        animationSpec = tween(500)
    )

    Surface(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f),
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 5.dp
    ) {
        when (metric.valueType) {
            MetricValueType.Progress -> {
                ProgressCounter(
                    metric = metric.copy(value = progressAnimated),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }

            MetricValueType.Text -> {
                TextCounter(
                    text = "${progressAnimated.toInt()}",
                    textColor = Color(0xFF023E8A),
                    boundaryValues = metric.boundaryValues,
                    name = metric.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
private fun GraphLegend(name: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = name, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
