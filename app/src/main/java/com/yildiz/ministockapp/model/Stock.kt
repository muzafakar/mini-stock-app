package com.yildiz.ministockapp.model

data class Stock(
    val symbol: String,
    val price: Double
)

data class StockWithPriceHistory(
    val symbol: String,
    val prices: List<Double>
)
