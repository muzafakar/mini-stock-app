package com.yildiz.ministockapp.util

import com.yildiz.ministockapp.model.Article
import com.yildiz.ministockapp.model.Ticker
import com.yildiz.ministockapp.model.TickerWithPriceHistory
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

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

fun Article.getFormattedDate(): String {
    // pattern: 2022-05-05T10:06:14Z
    val sdf = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())
    val instant = Instant.parse(publishedAt)
    val date = Date.from(instant)
    return sdf.format(date)
}