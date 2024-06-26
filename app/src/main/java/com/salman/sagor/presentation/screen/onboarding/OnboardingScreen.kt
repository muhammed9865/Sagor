package com.salman.sagor.presentation.screen.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.salman.sagor.presentation.composable.Screen
import com.salman.sagor.presentation.model.OnboardingPage
import com.salman.sagor.presentation.navigation.LocalNavigator
import com.salman.sagor.presentation.navigation.graphs.MainGraph
import com.salman.sagor.presentation.navigation.graphs.OnboardingGraph
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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
        navigator.navigate(MainGraph.route) {
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
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                key = { "onboarding_pager$it" },
                modifier = Modifier.fillMaxSize(),
            ) {
                val page = OnboardingPage[it]
                PageContent(page = page)
            }
            NextButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                page = pagerState.currentPage + 1
            ) {
                scope.launch {
                    val isLastPage = pagerState.currentPage == state.onboardingPages.size - 1
                    if (isLastPage) {
                        onAction(OnboardingAction.OnFinish)
                    } else {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        }
    }
}

@Composable
private fun PageContent(
    page: OnboardingPage,
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
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
private fun NextButton(
    modifier: Modifier = Modifier,
    page: Int,
    onClick: () -> Unit
) {
    val brush = remember {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFB2B2B2),
                Color(0xFF023E8A)
            )
        )
    }
    val endAngleAnimated by animateFloatAsState(targetValue = page * 90f, label = "pageAnimation")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(60.dp)
            .drawBehind {
                drawArc(
                    color = Color(0xFFE3E6E6),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(0.5.dp.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    brush = brush,
                    startAngle = -90f,
                    sweepAngle = endAngleAnimated,
                    useCenter = false,
                    style = Stroke(2.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        // Placeholder for the icon
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}