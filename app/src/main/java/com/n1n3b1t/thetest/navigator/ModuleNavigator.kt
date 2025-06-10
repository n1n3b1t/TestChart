package com.n1n3b1t.thetest.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface ModuleNavigator {
    fun setNavController(navController: NavController)
    fun graph(builder: NavGraphBuilder, navigator: Navigator)
}