package com.n1n3b1t.thetest.main.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.n1n3b1t.thetest.R
import com.n1n3b1t.thetest.core.EffectObserver
import com.n1n3b1t.thetest.navigator.Navigator
import com.n1n3b1t.thetest.ui.theme.TheTestTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navigator: Navigator, viewModel: MainScreenViewModel = koinViewModel()
) {
    val state = viewModel.viewState.value
    val listener = object : MainScreenContract.Listener {
        override fun onCountChange(count: Int) {
            viewModel.setEvent(MainScreenContract.Event.CountChange(count))
        }

        override fun onProceedClick() {
            viewModel.setEvent(MainScreenContract.Event.onProceedClick)
        }
    }
    EffectObserver(effect = viewModel.effect) {
        when (it) {
            is MainScreenContract.Effect.navigateToDataScreen -> {
                navigator.mainNavigator.navigateToDataScreen(it.count)
            }
        }
    }
    MainScreenContent(state, listener)
}

@Composable
fun MainScreenContent(
    state: MainScreenContract.State = MainScreenContract.State(),
    listener: MainScreenContract.Listener? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.enter_number_of_points),
            style = MaterialTheme.typography.bodyLarge

        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.count.toString(), modifier = Modifier, onValueChange = {
                listener?.onCountChange(it.toIntOrNull() ?: 0)
            }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { listener?.onProceedClick() }, enabled = state.count > 0
        ) {
            Text(text = stringResource(R.string.proceed))
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    TheTestTheme {
        Surface {
            MainScreenContent(
                state = MainScreenContract.State(count = 10),
                listener = object : MainScreenContract.Listener {
                    override fun onCountChange(count: Int) {}
                    override fun onProceedClick() {}
                }
            )
        }
    }
}
