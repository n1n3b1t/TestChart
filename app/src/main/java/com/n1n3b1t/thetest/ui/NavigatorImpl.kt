package com.n1n3b1t.thetest.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.n1n3b1t.thetest.main.navigation.MainScreens
import com.n1n3b1t.thetest.navigator.MainNavigator
import com.n1n3b1t.thetest.navigator.Navigator

class NavigatorImpl(
    override val mainNavigator: MainNavigator
) : Navigator {
    private lateinit var navController: NavHostController
    override fun setNavController(navHostController: NavHostController) {
        this.navController = navHostController
        mainNavigator.setNavController(navController)
    }

    @Composable
    override fun navHost() {
        NavHost(navController = navController, startDestination = MainScreens.rootRoute) {
            mainNavigator.graph(this, this@NavigatorImpl)
        }
    }

    override fun back() {
        navController.navigateUp()
    }
}