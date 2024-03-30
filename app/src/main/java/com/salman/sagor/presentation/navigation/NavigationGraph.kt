package com.salman.sagor.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 */
abstract class NavigationGraph(val startDestination: String, val route: String) {

    abstract fun navigation(navController: NavController, navGraphBuilder: NavGraphBuilder)
}