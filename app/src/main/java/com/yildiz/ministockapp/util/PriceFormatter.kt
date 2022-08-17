package com.yildiz.ministockapp.util

import com.yildiz.ministockapp.model.Ticker
import com.yildiz.ministockapp.model.TickerWithPriceHistory
import java.math.RoundingMode
import java.text.DecimalFormat

fun Ticker.getFormattedPrice(): String {
    val df = DecimalFormat("#.##").apply {
        roundingMode = RoundingMode.UP
    }
    return "${df.format(price)} USD"
}

fun TickerWithPriceHistory.getFormattedPrice(): String {
    val df = DecimalFormat("#.##").apply {
        roundingMode = RoundingMode.UP
    }
    return "${df.format(prices.last())} USD"
}