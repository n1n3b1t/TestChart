package com.n1n3b1t.thetest.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface Navigator {
    val mainNavigator: MainNavigator
    fun setNavController(navHostController: NavHostController)


    @Composable
    fun navHost()

    fun back()

}