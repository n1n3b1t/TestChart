import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.n1n3b1t.thetest.network.Point
import com.n1n3b1t.thetest.ui.theme.TheTestTheme
import kotlin.math.min


@Composable
fun MegaChart(
    points: List<Point>,
    modifier: Modifier = Modifier,
    ySteps: Int = 5,
    xSteps: Int = 5,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    axisColor: Color = MaterialTheme.colorScheme.primary,
    pointColor: Color = MaterialTheme.colorScheme.tertiary,
    lineColor: Color = MaterialTheme.colorScheme.secondary,
    lineStroke: Dp = 3.dp,
    pointRadius: Dp = 5.dp,
    textSize: TextUnit = 10.sp,
) {
    val tm = rememberTextMeasurer()
    val offset = tm.measure(
        text = "000.000", style = androidx.compose.ui.text.TextStyle(fontSize = textSize)
    ).size
    var zoom by remember { mutableFloatStateOf(points.size.toFloat() / min(30, points.size)) }
    var pan by remember { mutableFloatStateOf(0f) }

    val minX = points.minOfOrNull { it.x } ?: 0.0f
    val maxX = points.maxOfOrNull { it.x } ?: 0.0f
    val minY = points.minOfOrNull { it.y } ?: 0.0f
    val maxY = points.maxOfOrNull { it.y } ?: 0.0f


    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTransformGestures { _, panChange, zoomChange, _ ->
                    zoom *= zoomChange
                    if (panChange.x != 0f)
                        pan += panChange.x
                    else
                        pan = pan * zoomChange
                }
            }

    ) {


        Canvas(
            Modifier.matchParentSize()
        ) {

            val w = size.width
            val h = size.height

            val scaleX = if (maxX - minX == 0.0f) 1.0f else ((w - offset.width) / (maxX - minX))
            val scaleY = if (maxY - minY == 0.0f) 1.0f else ((h - offset.height) / (maxY - minY))

            drawLine(
                color = axisColor,
                start = Offset(offset.width.toFloat(), h - offset.height),
                end = Offset(w, h - offset.height),
                strokeWidth = lineStroke.toPx()
            )
            drawLine(
                color = axisColor,
                start = Offset(offset.width.toFloat(), 0f),
                end = Offset(offset.width.toFloat(), h - offset.height),
                strokeWidth = lineStroke.toPx()
            )
            (1..xSteps).forEach { i ->
                // Calculate the visible range of X values considering zoom and pan
                val visibleMinX = minX - (pan / (scaleX * zoom))
                val visibleMaxX = visibleMinX + ((w - offset.width) / (scaleX * zoom))

                // Calculate grid step based on visible range
                val step = (visibleMaxX - visibleMinX) / xSteps
                val value = visibleMinX + (step * i)

                val x = offset.width + ((value - minX) * scaleX * zoom) + pan

                // Only draw grid lines that are visible
                if (x >= offset.width && x <= w) {
                    drawLine(
                        color = axisColor,
                        start = Offset(x, h - offset.height),
                        end = Offset(x, 0f),
                        strokeWidth = lineStroke.toPx() / 2,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                            floatArrayOf(10f, 10f), phase = 0f
                        )
                    )

                    // Format differently based on number size
                    val format = "%.2f"
                    drawText(
                        text = format.format(value),
                        topLeft = Offset(x, h - offset.height),
                        style = androidx.compose.ui.text.TextStyle(
                            color = textColor, fontSize = textSize
                        ),
                        maxLines = 1,
                        textMeasurer = tm
                    )
                }
            }
            (1..ySteps).forEach { i ->
                val value = minY + (maxY - minY) * i / ySteps
                val y = h - ((value - minY) * scaleY) - offset.height
                drawLine(
                    color = axisColor,
                    start = Offset(offset.width.toFloat(), y),
                    end = Offset(w, y),
                    strokeWidth = lineStroke.toPx() / 2,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                        floatArrayOf(10f, 10f), phase = 0f
                    )

                )
                drawText(
                    text = "%.2f".format(value),
                    topLeft = Offset(0f, if (i == 0) y - offset.height else y),
                    style = androidx.compose.ui.text.TextStyle(
                        color = textColor, fontSize = textSize
                    ),
                    maxLines = 1,
                    textMeasurer = tm
                )
            }

            // Draw points
            if (points.size > 1) {
                val canvasRect = androidx.compose.ui.geometry.Rect(
                    offset = Offset(offset.width.toFloat(), 0f),
                    size = androidx.compose.ui.geometry.Size(
                        width = w - offset.width, height = h - offset.height
                    )
                )

                val smoothPoints = points.map { point ->
                    Offset(
                        x = offset.width + ((point.x - minX) * scaleX * zoom) + pan,
                        y = h - ((point.y - minY) * scaleY) - offset.height
                    )
                }


                // Create path with all points to ensure continuity
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(smoothPoints[0].x, smoothPoints[0].y)
                    for (i in 1 until smoothPoints.size) {
                        lineTo(smoothPoints[i].x, smoothPoints[i].y)
                    }
                }

                // Draw path with clipping
                clipRect(
                    left = offset.width.toFloat(), top = 0f, right = w, bottom = h - offset.height
                ) {
                    drawPath(
                        path = path,
                        color = lineColor,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = lineStroke.toPx())
                    )
                }
                // Draw visible points
                smoothPoints.forEach { point ->
                    if (point.x in canvasRect.left..canvasRect.right && point.y in canvasRect.top..canvasRect.bottom) {
                        drawCircle(
                            color = pointColor, radius = pointRadius.toPx(), center = point
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun MegaChartPreview() {
    val points = List(100) { i ->
        Point(
            x = i.toFloat(), y = (Math.cos(i.toDouble() / 10) * 50 + 50).toFloat()
        )
    }
    TheTestTheme(darkTheme = true) {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                TestTable(
                    points.map { Pair(it.x, it.y) }, modifier = Modifier.weight(1f)
                )
                MegaChart(
                    points = points, modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    }


}

@Preview
@Composable
fun MegaChartPreview2() {
    val points = listOf(
        Point(1.0f, 2.0f),
        Point(22.0f, 3.0f),
        Point(33.0f, 12.0f),
        Point(45.0f, 112.0f),
        Point(56.0f, 4.0f),
    )
    TheTestTheme(darkTheme = false) {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                TestTable(
                    points.map { Pair(it.x, it.y) }, modifier = Modifier.weight(1f)
                )
                MegaChart(
                    points = points, modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    }


}
