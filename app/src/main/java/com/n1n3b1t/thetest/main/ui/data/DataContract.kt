package com.n1n3b1t.thetest.main.ui.data

import com.n1n3b1t.thetest.core.ViewEvent
import com.n1n3b1t.thetest.core.ViewSideEffect
import com.n1n3b1t.thetest.core.ViewState
import com.n1n3b1t.thetest.main.ui.error.ErrorType
import com.n1n3b1t.thetest.network.Point

class DataContract {
    sealed interface Event : ViewEvent {
        data class SetArgs(val numberOfPoints: Int) : Event
    }

    sealed interface Effect : ViewSideEffect {
        data class ShowError(val errorType: ErrorType) : Effect
    }

    data class State(
        val numberOfPoints: Int = 0,
        val points: List<Point> = emptyList(),
        val isLoading: Boolean = false,
    ) : ViewState
}