package com.yildiz.ministockapp.model

data class StockWithHistory(
    val ticker: String?,
    val prices: List<Double?>
)
