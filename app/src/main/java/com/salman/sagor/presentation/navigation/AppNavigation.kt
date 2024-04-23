package com.salman.sagor.presentation.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.salman.sagor.presentation.navigation.graphs.AuthGraph
import com.salman.sagor.presentation.navigation.graphs.MainGraph
import com.salman.sagor.presentation.navigation.graphs.OnboardingGraph

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 *
 * @param intent The intent that is used to navigate to a specific screen (deep link)
 */
@Composable
fun AppNavigation(intent: Intent? = null) {
    val navController = rememberNavController()
    val graphs = listOf(
        MainGraph,
        OnboardingGraph,
        AuthGraph
    )
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

val LocalNavigator = staticCompositionLocalOf<NavHostController> {
    error("LocalNavigator not initialized")
}