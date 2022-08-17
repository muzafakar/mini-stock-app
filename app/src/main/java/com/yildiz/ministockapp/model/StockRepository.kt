package com.yildiz.ministockapp.model

import android.content.Context
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

interface StockRepository {
   suspend fun loadStocks(): List<Stock>
}

class StockRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StockRepository {

    override suspend fun loadStocks(): List<Stock> {
        val stocks = mutableListOf<Stock>()
        val csvFile = context.assets.open("stocks.csv")
        csvReader().openAsync(csvFile) {
            readAllWithHeaderAsSequence().forEach {
                val ticker = it["STOCK"].toString()
                val price = it["PRICE"]?.toDoubleOrNull() ?: 0.0
                stocks.add(Stock(ticker, price))
            }
        }

        return stocks.toList().shuffled(Random(Date().time))
    }
}