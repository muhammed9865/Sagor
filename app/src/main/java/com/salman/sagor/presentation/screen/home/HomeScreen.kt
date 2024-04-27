package com.salman.sagor.presentation.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.compose.dropUnlessStarted
import com.salman.sagor.R
import com.salman.sagor.domain.model.Pool
import com.salman.sagor.presentation.composable.Graph
import com.salman.sagor.presentation.composable.Screen
import com.salman.sagor.presentation.navigation.LocalNavigator
import com.salman.sagor.presentation.navigation.graphs.MainGraph

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val pools by viewModel.pools.collectAsState()
    val navigator = LocalNavigator.current

    Screen(
        title = stringResource(R.string.home),
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notification"
                )
            }
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pools) {
                PoolItem(pool = it, onClicked = dropUnlessResumed {
                    navigator.navigate(MainGraph.Routes.pool(it.id))
                })
            }
        }
    }
}

@Composable
private fun PoolItem(
    pool: Pool,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit = {}
) {
    Surface(
        modifier
            .fillMaxWidth()
            .aspectRatio(1.7777f)
            .clickable { onClicked() },
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = pool.name)
            Graph(
                xValues = pool.xValues,
                yValues = pool.yValues,
                values = pool.history,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            OutlinedButton(
                onClick = onClicked,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(R.string.view),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

