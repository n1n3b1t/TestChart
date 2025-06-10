package com.n1n3b1t.thetest.main.ui.home

import com.n1n3b1t.thetest.core.BaseViewModel

class MainScreenViewModel :
    BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {
    override fun setInitialState(): MainScreenContract.State = MainScreenContract.State(10)
    override fun handleEvents(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.CountChange -> {
                setState {
                    copy(count = event.count)
                }
            }

            MainScreenContract.Event.onProceedClick -> {
                setEffect { MainScreenContract.Effect.navigateToDataScreen(viewState.value.count) }
            }
        }
    }
}