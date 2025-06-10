package com.n1n3b1t.thetest.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.n1n3b1t.thetest.main.ui.data.DataScreen
import com.n1n3b1t.thetest.main.ui.error.ErrorType
import com.n1n3b1t.thetest.main.ui.home.MainScreen
import com.n1n3b1t.thetest.navigator.Navigator
import com.n1n3b1t.thetest.navigator.Screen

private const val KEY_ARG = "key_arg"

sealed class MainScreens<T>(
    override val route: String,
    override val rootRoute: String = Companion.rootRoute
) : Screen<T> {
    companion object {
        const val rootRoute = "main_nav"
    }

    object HomeScreen : MainScreens<Unit>("home") {
        override fun navigate(args: Unit, navController: NavController) {
            navController.navigate(route)
        }

        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(route) {
                MainScreen(
                    navigator = navigator
                )
            }
        }
    }

    object DataScreen : MainScreens<Int>("data/{$KEY_ARG}") {
        override fun navigate(args: Int, navController: NavController) {
            navController.navigate(route.replace("{$KEY_ARG}", args.toString()))
        }

        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route,
                listOf(navArgument(KEY_ARG) { type = NavType.IntType })
            ) {
                val count = it.arguments?.getInt(KEY_ARG) ?: 0
                DataScreen(navigator, count)
            }
        }
    }

    object ErrorScreen : MainScreens<ErrorType>("error/{$KEY_ARG}") {
        override fun navigate(args: ErrorType, navController: NavController) {
            navController.navigate(route.replace("{$KEY_ARG}", args.name)) {
                // Popup to the home screen
                popUpTo(MainScreens.HomeScreen.route) {
                    inclusive = false
                }
            }
        }

        override fun createScreen(navGraphBuilder: NavGraphBuilder, navigator: Navigator) {
            navGraphBuilder.composable(
                route,
                listOf(navArgument(KEY_ARG) { type = NavType.StringType })
            ) {
                val errorType = ErrorType.valueOf(it.arguments?.getString(KEY_ARG) ?: "Unknown")
                com.n1n3b1t.thetest.main.ui.error.ErrorScreen(errorType, navigator)
            }
        }
    }

}