package com.salman.sagor.presentation.navigation.graphs

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.salman.sagor.presentation.navigation.NavigationGraph

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 */
object MainGraph : NavigationGraph(startDestination = "home", route = "main") {

    override fun navigation(navController: NavController, navGraphBuilder: NavGraphBuilder) =
        with(navGraphBuilder) {
            navigation(
                startDestination = this@MainGraph.startDestination,
                route = this@MainGraph.route,
            ) {
                composable("home",
                    deepLinks = listOf(
                        navDeepLink {
                            uriPattern = "https://salman.com/main/{id}"
                        }
                    )
                ) {
                    val id = it.arguments?.getString("id")
                    val text =
                        if (id != null) "Home Screen From Pending Intent $id" else "Home Screen"
                    Text(text = text)
                }
            }
        }

}