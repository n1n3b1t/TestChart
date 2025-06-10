package com.n1n3b1t.thetest.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.n1n3b1t.thetest.main.ui.error.ErrorType
import com.n1n3b1t.thetest.navigator.MainNavigator
import com.n1n3b1t.thetest.navigator.Navigator

class MainNavigatorImpl() : MainNavigator {
    private lateinit var navController: NavController
    override fun navigateToDataScreen(count: Int) {
        MainScreens.DataScreen.navigate(count, navController)
    }

    override fun navigateToError(title: String, message: String) {
        MainScreens.HomeScreen.navigate(Unit, navController)
    }

    override fun navigateToStart() {
        MainScreens.HomeScreen.navigate(Unit, navController)
    }

    override fun navigateToError(errorType: ErrorType) {
        MainScreens.ErrorScreen.navigate(errorType, navController)
    }

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun graph(builder: NavGraphBuilder, navigator: Navigator) {
        builder.navigation(MainScreens.HomeScreen.route, MainScreens.rootRoute) {
            MainScreens.HomeScreen.createScreen(
                this,
                navigator
            )
            MainScreens.DataScreen.createScreen(
                this,
                navigator
            )
            MainScreens.ErrorScreen.createScreen(
                this,
                navigator
            )
        }
    }
}