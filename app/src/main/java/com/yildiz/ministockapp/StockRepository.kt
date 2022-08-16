package com.yildiz.ministockapp

import android.content.Context
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.yildiz.ministockapp.model.Ticker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StockRepository {
   suspend fun loadStocks(): List<Ticker>
}

class StockRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StockRepository {


    override suspend fun loadStocks(): List<Ticker> {
        val tickers = mutableListOf<Ticker>()
        val csvFile = context.assets.open("stocks.csv")
        csvReader().openAsync(csvFile) {
            readAllWithHeaderAsSequence().forEach {
                val ticker = it["STOCK"].toString()
                val price = it["PRICE"]?.toDoubleOrNull() ?: 0.0
                tickers.add(Ticker(ticker, price))
            }
        }

        return tickers.toList()
    }
}