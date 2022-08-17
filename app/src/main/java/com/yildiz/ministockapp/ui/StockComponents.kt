package com.yildiz.ministockapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
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
import com.yildiz.ministockapp.model.StockWithPriceHistory
import com.yildiz.ministockapp.util.Const.MAX_VISIBLE_PRICE
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
            itemsIndexed(stocks.value) { index, stock ->
                StockItem(stock = stock, shouldObserverPrice = index == 0, index = index)
            }
        }
    }
}


@Composable
private fun StockItem(
    stock: StockWithPriceHistory,
    shouldObserverPrice: Boolean,
    stockViewModel: StockViewModel = viewModel(),
    index: Int
) {
    val realtimeStock = stockViewModel.realtimeStock.collectAsState()

    LaunchedEffect(stock.symbol) {
        if (shouldObserverPrice) {
            stockViewModel.observeStockPrice(stock.symbol)
        }
    }

    val gradientBrush = Brush.verticalGradient(
        listOf(getGradientColor(index).copy(alpha = ContentAlpha.medium), getGradientColor(index))
    )
    Column(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .background(
                brush = gradientBrush,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = (if (shouldObserverPrice) realtimeStock.value else stock)?.symbol.toString(),
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        StockPrice(
            price = (if (shouldObserverPrice) realtimeStock.value else stock)?.getFormattedPrice()
                .toString(),
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(10.dp))

        val prices =
            (if (shouldObserverPrice) realtimeStock.value else stock)?.prices ?: emptyList()
        if (prices.isNotEmpty()) {
            StockChart(prices)
        }
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
        colors = graphLineColor,
        paddingValues = PaddingValues(0.dp)
    )

    val data = prices.takeLast(MAX_VISIBLE_PRICE)

    LineGraph(
        xAxisData = data.map { GraphData.Number(it) },
        yAxisData = data,
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

private fun getGradientColor(index: Int): Color {

    return listOf(
        Color(0xFF9C27B0),
        Color(0xFF4CAF50),
        Color(0xFF2196F3),
        Color(0xFFFF9800),
        Color(0xFF00BCD4),
        Color(0xFFE91E63),
        Color(0xFF3F51B5),
    )[index]
}