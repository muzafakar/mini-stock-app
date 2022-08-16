package com.yildiz.ministockapp.usecase

import com.yildiz.ministockapp.StockRepository
import com.yildiz.ministockapp.model.Ticker
import com.yildiz.ministockapp.model.TickerWithPriceHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

interface StockUseCase {
    suspend fun getAllStocks(): List<Ticker>
    suspend fun getUniqueStocks(): List<Ticker>
    suspend fun getAllStocksWithHistory(limit: Int): List<TickerWithPriceHistory>
    suspend fun getRealTimeStock(ticker: String): Flow<Ticker>
}

class StockUseCaseImpl @Inject constructor(
    private val stockRepository: StockRepository
) : StockUseCase {
    override suspend fun getAllStocks(): List<Ticker> {
        return stockRepository.loadStocks()
    }

    override suspend fun getUniqueStocks(): List<Ticker> {
        return stockRepository.loadStocks()
            .groupBy { it.symbol }
            .map { Ticker(it.key, it.value.first().price) }

    }

    override suspend fun getAllStocksWithHistory(limit: Int): List<TickerWithPriceHistory> {
        return runCatching {
            stockRepository.loadStocks().groupBy { it.symbol }
                .map {
                    val ticker = it.key
                    val prices = it.value.map { stock -> stock.price }.slice(0 until limit)
                    TickerWithPriceHistory(ticker, prices)
                }
        }.getOrElse {
            emptyList()
        }
    }

    override suspend fun getRealTimeStock(ticker: String): Flow<Ticker> {
        val stocks = stockRepository.loadStocks().filter { it.symbol == ticker }
        return stocks.asFlow()
    }

}