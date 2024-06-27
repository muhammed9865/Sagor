package com.salman.sagor.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.dropUnlessResumed
import com.salman.sagor.R
import com.salman.sagor.domain.model.Tank
import com.salman.sagor.presentation.composable.BorderedSurface
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
    val state by viewModel.state.collectAsState()
    val navigator = LocalNavigator.current

    Screen(
        title = stringResource(R.string.home),
        actions = {
            IconButton(onClick = {
                navigator.navigate(MainGraph.Routes.alerts)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notification"
                )
            }
        },
        onEnteringScreen = { viewModel.handleAction(HomeAction.Reload) },
        onLeavingScreen = { viewModel.handleAction(HomeAction.StopFetching) }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.tanks) {
                PoolItem(tank = it, onClicked = dropUnlessResumed {
                    navigator.navigate(MainGraph.Routes.pool(it.id))
                })
            }
        }
        if (state.showEmptyTanksList) {
            EmptyTankList(onAction = viewModel::handleAction)
        }
        if (state.isLoading) {
            LoadingTanks()
        }
    }
}

@Composable
private fun PoolItem(
    tank: Tank,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit = {}
) {
    BorderedSurface(modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = tank.name)
            Graph(
                values = tank.packages.first().sensorsHistory,
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

@Composable
private fun EmptyTankList(
    modifier: Modifier = Modifier,
    onAction: (HomeAction) -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_tanks_found_please_contact_us_for_more_information),
            textAlign = TextAlign.Center
        )
        OutlinedButton(onClick = { onAction(HomeAction.Reload) }) {
            Text(text = stringResource(R.string.reload))
        }
    }
}

@Composable
private fun LoadingTanks() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        LinearProgressIndicator()
    }
}

