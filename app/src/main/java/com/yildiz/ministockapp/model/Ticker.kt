package com.yildiz.ministockapp.model

import kotlinx.serialization.Serializable

data class Ticker(
    val symbol: String,
    val price: Double
)

data class TickerWithPriceHistory(
    val symbol: String,
    val prices: List<Double>
)
