package com.n1n3b1t.thetest.main.ui.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.n1n3b1t.thetest.R
import com.n1n3b1t.thetest.navigator.Navigator
import com.n1n3b1t.thetest.ui.theme.TheTestTheme

enum class ErrorType {
    NETWORK_ERROR,
    POINT_ERROR,
    UNKNOWN_ERROR
}

@Composable
fun ErrorScreen(
    errorType: ErrorType,
    navigator: Navigator? = null,
) {
    val errorMessage = when (errorType) {
        ErrorType.NETWORK_ERROR -> stringResource(R.string.error_no_internet)
        ErrorType.POINT_ERROR -> stringResource(R.string.error_wrong_number_of_points)
        ErrorType.UNKNOWN_ERROR -> stringResource(R.string.error_unknown)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Error Icon",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = { navigator?.back() }) {
            Text(text = stringResource(R.string.go_home))
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    TheTestTheme {
        Surface {
            ErrorScreen(
                errorType = ErrorType.NETWORK_ERROR,
            )
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreviewPointError() {
    TheTestTheme {
        Surface {
            ErrorScreen(
                errorType = ErrorType.POINT_ERROR,
            )
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreviewUnknownError() {
    TheTestTheme {
        Surface {
            ErrorScreen(
                errorType = ErrorType.UNKNOWN_ERROR,
            )
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreviewDarkTheme() {
    TheTestTheme(darkTheme = true) {
        Surface {
            ErrorScreen(
                errorType = ErrorType.NETWORK_ERROR,
            )
        }
    }
}