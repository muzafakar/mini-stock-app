package com.yildiz.ministockapp.ui.screen

import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.log

/*fun lineChartData() = listOf(
    5929, -6898, 8961, 5674, 7122, 2092, 3427, 5520, 4680, 7418,
    4743, 4280, -12211, 7295, 9900, 12438, -11186, 5439, 4227, 5138,
    11015, -8386, 12450, 10411, 10852, -7782, 7371, 4983, 9234, 6847
).map { it.toDouble() }*/

fun lineChartData() = listOf(
    300, 700, 120, -600, 520
).map { it.toDouble() }

@Composable
fun MyChart(data: List<Double>) {
    if (data.isEmpty()) return
    Card{
        Column(
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
        ) {
            Canvas(
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
                    .background(Color.Cyan)
            ) {
                Log.d(">>>", "size: ${size.width} ${size.height} ")
                val maxValue: Double = data.maxOrNull() ?: 0.0
                Log.d(">>>", "max: $maxValue")
                val minValue: Double = data.minOrNull() ?: 0.0
                Log.d(">>>", "min: $minValue")
                val yDistance = size.height / (maxValue - minValue)
                Log.d(">>>", "yD: ${maxValue - minValue}")
                Log.d(">>>", "yD: $yDistance")
                val xDistance = size.width / data.size
                Log.d(">>>", "xD: $xDistance")
                var currentX = 0F

                val points = mutableListOf<PointF>()

                data.forEachIndexed { index, d ->
                    val x = currentX
                    val y = yDistance * d
                    points.add(PointF(x, y.toFloat()))
                    currentX += xDistance
                }
                Log.d(">>>", "MyChart: $points")

                for (i in 0 until points.size - 1) {
                    drawLine(
                        start = Offset(points[i].x, points[i].y),
                        end = Offset(points[i + 1].x, points[i + 1].y),
                        color = Color(0xFF3F51B5),
                        strokeWidth = 8f
                    )
                }
            }

        }
    }


}

fun adjustData(prices: List<Double>): List<Double> {
    val min = prices.minOrNull() ?: 0.0
    return prices.map { it + min }
}