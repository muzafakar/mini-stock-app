package com.yildiz.ministockapp

import android.content.Context
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.yildiz.ministockapp.model.Stock
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StockRepository {
    fun loadStocks()
    val stocks: List<Stock>
}

class StockRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StockRepository {
    private var _stocks = sequenceOf<Stock>()
    override val stocks: List<Stock>
        get() = _stocks.toList()

    override fun loadStocks() {
        val csvFile = context.assets.open("stocks.csv")
        csvReader().open(csvFile) {
            _stocks = readAllWithHeaderAsSequence().map {
                val ticker = it["STOCK"]
                val price = it["PRICE"]?.toDoubleOrNull()
                Stock(ticker, price)
            }
        }
    }
}