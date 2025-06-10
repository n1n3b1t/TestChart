package com.n1n3b1t.thetest.main.ui.data

import android.util.Log
import com.n1n3b1t.thetest.core.BaseViewModel
import com.n1n3b1t.thetest.main.domain.usecase.GetPointsUseCase
import com.n1n3b1t.thetest.main.domain.usecase.WrongPointsException
import com.n1n3b1t.thetest.main.ui.error.ErrorType
import okio.IOException

class DataViewModel(
    private val getPointsUseCase: GetPointsUseCase
) : BaseViewModel<DataContract.Event, DataContract.State, DataContract.Effect>(
) {
    override fun setInitialState(): DataContract.State = DataContract.State()

    override fun handleEvents(event: DataContract.Event) {
        when (event) {
            is DataContract.Event.SetArgs -> {
                if (viewState.value.points.isNotEmpty())
                    return
                setState {
                    copy(
                        numberOfPoints = event.numberOfPoints, isLoading = true
                    )
                }
                getPoints(event.numberOfPoints)
            }
        }
    }

    private fun getPoints(count: Int) {
        io {
            runCatching {
                getPointsUseCase.execute(count)
            }.onSuccess { points ->
                setState {
                    copy(
                        points = points,
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                Log.e("DataViewModel", "Error fetching points", error)
                when (error) {
                    is WrongPointsException -> {
                        setEffect { DataContract.Effect.ShowError(ErrorType.POINT_ERROR) }
                    }

                    is IOException -> {
                        setEffect { DataContract.Effect.ShowError(ErrorType.NETWORK_ERROR) }
                    }

                    else -> {
                        setEffect { DataContract.Effect.ShowError(ErrorType.UNKNOWN_ERROR) }
                    }
                }
            }
        }
    }

}