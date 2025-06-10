import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestTable(points: List<Pair<Float, Float>>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        // Header row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                Text("Index", modifier = Modifier.weight(0.3f))
                Text("X", modifier = Modifier.weight(1f))
                Text("Y", modifier = Modifier.weight(1f))
            }
        }

        // Data rows
        itemsIndexed(points) { index, point ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                Text(index.toString(), modifier = Modifier.weight(0.3f))
                Text(point.first.toString(), modifier = Modifier.weight(1f))
                Text(point.second.toString(), modifier = Modifier.weight(1f))
            }
        }
    }
}