package com.yildiz.ministockapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility
import com.yildiz.ministockapp.model.TickerWithPriceHistory
import com.yildiz.ministockapp.util.getFormattedPrice
import com.yildiz.ministockapp.viewModel.StockViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun StockSection(stockViewModel: StockViewModel = viewModel()) {
    val stocks = stockViewModel.stocks.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        stockViewModel.loadStock()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Watch list",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
            state = lazyListState,
            flingBehavior = rememberSnapperFlingBehavior(lazyListState),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(stocks.value) { stock ->
                StockItem(ticker = stock, modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
private fun StockItem(ticker: TickerWithPriceHistory, modifier: Modifier) {
    var brush: Brush = getBackgroundGradient()
    LaunchedEffect(key1 = ticker) {
        brush = getBackgroundGradient()
    }

    Column(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .background(
                brush = brush,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = ticker.symbol,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        StockPrice(
            price = ticker.getFormattedPrice(),
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(10.dp))
        //        StockChart(ticker.prices)
    }
}

@Composable
private fun StockChart(prices: List<Double>) {
    val graphLineColor = LinearGraphColors(
        lineColor = Color.White,
        pointColor = Color.Transparent,
        clickHighlightColor = Color.Transparent
    )

    val graphStyle = LineGraphStyle(
        visibility = LinearGraphVisibility(
            isHeaderVisible = false,
            isYAxisLabelVisible = false,
            isXAxisLabelVisible = false,
            isCrossHairVisible = false
        ),
        height = 50.dp,
        colors = graphLineColor
    )

    LineGraph(
        xAxisData = prices.map { GraphData.Number(it) },
        yAxisData = prices,
        style = graphStyle
    )
}

@Composable
private fun StockPrice(price: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .background(color = Color.White, shape = RoundedCornerShape(50))
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(text = price, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
    }
}

private fun getBackgroundGradient(): Brush {
    val colors = listOf(
        Color(0xFF9C27B0),
        Color(0xFF4CAF50),
        Color(0xFF2196F3),
        Color(0xFFFF9800),
        Color(0xFF00BCD4),
        Color(0xFFE91E63),
    ).random()

    return Brush.verticalGradient(
        listOf(
            colors.copy(alpha = 0.2f),
            colors
        )
    )
}