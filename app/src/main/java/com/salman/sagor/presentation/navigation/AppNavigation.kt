package com.salman.sagor.presentation.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.salman.sagor.presentation.model.NavigationParams
import com.salman.sagor.presentation.navigation.graphs.MainGraph
import com.salman.sagor.presentation.navigation.graphs.OnboardingGraph

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 *
 * @param intent The intent that is used to navigate to a specific screen (deep link)
 * @param navigationParams The navigation params that are used to determine the initial screen
 */
@Composable
fun AppNavigation(
    intent: Intent? = null,
    navigationParams: NavigationParams,
) {
    val navController = rememberNavController()
    val graphs = createNavigationGraphs(navigationParams)

    LaunchedEffect(key1 = intent?.data) {
        if (intent?.data != null) {
            navController.navigate(intent.data.toString())
        }
    }
    CompositionLocalProvider(LocalNavigator provides navController) {
        NavHost(navController = navController, startDestination = graphs.first().route) {
            graphs.forEach { it.navigation(navController, this) }
        }
    }
}

private fun createNavigationGraphs(navigationParams: NavigationParams): List<NavigationGraph> {
    val graphs = mutableListOf<NavigationGraph>()
    graphs.add(MainGraph)

    // TODO: Uncomment this when Auth feature is needed يا خسارة
    /*if (!navigationParams.isUserLoggedIn) {
        graphs.add(0, AuthGraph)
    }*/
    if (!navigationParams.isOnboardingComplete) {
        graphs.add(0, OnboardingGraph)
    }
    return graphs
}

val LocalNavigator = staticCompositionLocalOf<NavHostController> {
    error("LocalNavigator not initialized")
}