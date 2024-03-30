package com.salman.sagor.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.salman.sagor.presentation.navigation.NavigationGraph

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 */
object AuthGraph : NavigationGraph(startDestination = "login", route = "auth") {

    override fun navigation(navController: NavController, navGraphBuilder: NavGraphBuilder) =
        with(navGraphBuilder) {
            navigation(
                startDestination = this@AuthGraph.startDestination,
                route = this@AuthGraph.route
            ) {
                
            }
        }
}