package com.salman.sagor.presentation.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.salman.sagor.R
import com.salman.sagor.presentation.composable.Screen
import com.salman.sagor.presentation.model.OnboardingPage
import com.salman.sagor.presentation.navigation.LocalNavigator
import com.salman.sagor.presentation.navigation.graphs.AuthGraph
import com.salman.sagor.presentation.navigation.graphs.OnboardingGraph
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navigator = LocalNavigator.current
    OnboardingSlider(state = state, onAction = viewModel::handleAction)

    if (state.onboardingFinished) {
        navigator.navigate(AuthGraph.route) {
            popUpTo(OnboardingGraph.route) { inclusive = true }
        }
    }
}

@Composable
private fun OnboardingSlider(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { state.onboardingPages.size },
    )

    val scope = rememberCoroutineScope()
    snapshotFlow {
        pagerState.currentPage
    }.onEach {
        onAction(OnboardingAction.OnPageChanged(it))
    }.launchIn(scope)

    Screen {
        HorizontalPager(
            state = pagerState,
            key = { "onboarding_pager$it" },
            modifier = Modifier.fillMaxSize(),
        ) {
            val page = OnboardingPage[it]
            PageContent(page = page, isLastPage = it == pagerState.pageCount - 1, onAction = onAction)
        }
    }
}

@Composable
private fun PageContent(
    page: OnboardingPage,
    isLastPage: Boolean = false,
    onAction: (OnboardingAction) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = page.image), contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.8f)
        )
        Text(
            text = stringResource(id = page.description),
            style = MaterialTheme.typography.bodyLarge
        )
        if (isLastPage) {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { onAction(OnboardingAction.OnFinish) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = stringResource(id = R.string.onboarding_finish))
            }
        }
    }
}