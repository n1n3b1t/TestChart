package com.n1n3b1t.thetest.main.ui.data

import MegaChart
import TestTable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.n1n3b1t.thetest.core.EffectObserver
import com.n1n3b1t.thetest.navigator.Navigator
import com.n1n3b1t.thetest.network.Point
import com.n1n3b1t.thetest.ui.theme.TheTestTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DataScreen(
    navigator: Navigator,
    count: Int,
    viewModel: DataViewModel = koinViewModel()
) {
    EffectObserver(viewModel.effect) {
        when (it) {
            is DataContract.Effect.ShowError -> {
                navigator.mainNavigator.navigateToError(it.errorType)
            }
        }
    }
    LaunchedEffect(count) {
        viewModel.setEvent(DataContract.Event.SetArgs(numberOfPoints = count))
    }
    DataContent(
        state = viewModel.viewState.value,
        onBackClick = {
            navigator.back()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataContent(
    state: DataContract.State,
    onBackClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row() {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onBackClick?.invoke()
                    }
            )
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column {
                TestTable(state.points.map { Pair(it.x, it.y) }, Modifier.weight(1f))
                MegaChart(
                    state.points,
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }
}

@Preview
@Composable
fun DataScreenPreview() {
    TheTestTheme {
        Surface {
            DataContent(
                state = DataContract.State(
                    isLoading = false,
                    points = listOf(
                        Point(1f, 2f),
                        Point(2f, 3f),
                        Point(3f, 4f),
                        Point(4f, 5f)
                    )
                )
            )
        }
    }
}