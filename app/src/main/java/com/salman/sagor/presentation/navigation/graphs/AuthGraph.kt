package com.salman.sagor.presentation.navigation.graphs

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.salman.sagor.presentation.navigation.NavigationGraph
import com.salman.sagor.presentation.screen.login.LoginScreen
import com.salman.sagor.presentation.screen.login.VerifyLoginScreen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 */
object AuthGraph : NavigationGraph(startDestination = "login", route = Graphs.auth) {

    override fun navigation(navController: NavController, navGraphBuilder: NavGraphBuilder) =
        with(navGraphBuilder) {
            navigation(
                startDestination = this@AuthGraph.startDestination,
                route = this@AuthGraph.route
            ) {

                composable("login") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(this@AuthGraph.route)
                    }
                    LoginScreen(viewModel = hiltViewModel(parentEntry))
                }

                composable("verify") {
                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(this@AuthGraph.route)
                    }
                    VerifyLoginScreen(viewModel = hiltViewModel(parentEntry))
                }
            }
        }
}