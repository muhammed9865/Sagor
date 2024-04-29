package com.salman.sagor.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.salman.sagor.presentation.navigation.NavigationGraph
import com.salman.sagor.presentation.screen.onboarding.OnboardingScreen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
object OnboardingGraph :
    NavigationGraph(startDestination = "onboarding", route = Graphs.onboarding) {

    override fun navigation(navController: NavController, navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            composable("onboarding") {
                OnboardingScreen()
            }
        }
    }
}