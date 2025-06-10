package com.n1n3b1t.thetest.navigator

import com.n1n3b1t.thetest.main.ui.error.ErrorType

interface MainNavigator : ModuleNavigator {
    fun navigateToDataScreen(count: Int)
    fun navigateToError(title: String, message: String)
    fun navigateToStart()
    fun navigateToError(errorType: ErrorType)
}