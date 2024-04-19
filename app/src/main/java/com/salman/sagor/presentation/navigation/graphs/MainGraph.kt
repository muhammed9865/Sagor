package com.salman.sagor.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.salman.sagor.presentation.navigation.NavigationGraph
import com.salman.sagor.presentation.screen.home.HomeScreen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 */
object MainGraph : NavigationGraph(startDestination = Routes.home, route = "main") {

    object Routes {
        const val home = "home"
    }

    override fun navigation(navController: NavController, navGraphBuilder: NavGraphBuilder) =
        with(navGraphBuilder) {
            navigation(
                startDestination = this@MainGraph.startDestination,
                route = this@MainGraph.route,
            ) {
                composable(
                    Routes.home,
                    deepLinks = listOf(
                        navDeepLink {
                            uriPattern = "https://salman.com/main/{id}"
                        }
                    )
                ) {
                    HomeScreen()
                }
            }
        }
}