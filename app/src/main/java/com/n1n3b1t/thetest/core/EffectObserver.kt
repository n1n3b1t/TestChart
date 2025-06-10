package com.n1n3b1t.thetest.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun <T : ViewSideEffect> EffectObserver(effect: Flow<T>, onEach: (T) -> Unit) {
    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effect.onEach {
            onEach(it)
        }.collect()
    }
}