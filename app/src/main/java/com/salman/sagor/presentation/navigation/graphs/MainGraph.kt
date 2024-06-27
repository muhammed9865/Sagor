package com.salman.sagor.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.salman.sagor.presentation.navigation.NavigationGraph
import com.salman.sagor.presentation.screen.alerts.AlertsScreen
import com.salman.sagor.presentation.screen.home.HomeScreen
import com.salman.sagor.presentation.screen.pool.PoolScreen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 */
object MainGraph : NavigationGraph(startDestination = Routes.home, route = Graphs.main) {

    object Routes {
        const val home = "home"
        const val alerts = "alerts"
        fun pool(id: Int = -1) = "pool/${if (id == -1) "{id}" else id}"
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

                composable(
                    Routes.pool(),
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) {
                    val id = it.arguments?.getInt("id") ?: -1
                    PoolScreen(id)
                }

                composable(Routes.alerts) {
                    AlertsScreen()
                }
            }
        }
}