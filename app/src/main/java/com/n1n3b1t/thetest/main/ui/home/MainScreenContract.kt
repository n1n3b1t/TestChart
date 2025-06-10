package com.n1n3b1t.thetest.main.ui.home

import com.n1n3b1t.thetest.core.ViewEvent
import com.n1n3b1t.thetest.core.ViewSideEffect
import com.n1n3b1t.thetest.core.ViewState

class MainScreenContract {
    sealed interface Event : ViewEvent {
        data class CountChange(val count: Int) : Event
        data object onProceedClick : Event
    }

    sealed interface Effect : ViewSideEffect {
        data class navigateToDataScreen(val count: Int) : Effect
    }

    data class State(
        val count: Int = 0,
    ) : ViewState

    interface Listener {
        fun onCountChange(count: Int)
        fun onProceedClick()
    }
}