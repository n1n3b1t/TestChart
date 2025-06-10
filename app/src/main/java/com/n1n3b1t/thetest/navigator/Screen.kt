package com.n1n3b1t.thetest.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface Screen<T> {
    val route: String
    val rootRoute: String?
    fun navigate(args: T, navController: NavController)

    fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator)
}

fun Screen<Unit>.navigate(navController: NavController) {
    navigate(Unit, navController)
}