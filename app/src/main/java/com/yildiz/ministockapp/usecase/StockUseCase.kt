package com.yildiz.ministockapp.usecase

import com.yildiz.ministockapp.model.StockRepository
import com.yildiz.ministockapp.model.Stock
import com.yildiz.ministockapp.model.StockWithPriceHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

interface StockUseCase {
    suspend fun getAllStocks(): List<Stock>
    suspend fun getUniqueStocks(): List<Stock>
    suspend fun getAllStocksWithPriceHistory(limit: Int): List<StockWithPriceHistory>
    suspend fun getStockWithPriceHistory(stockId: String, limit: Int): List<Double>?
    suspend fun getRealTimeStock(ticker: String): Flow<Stock>
}

class StockUseCaseImpl @Inject constructor(
    private val stockRepository: StockRepository
) : StockUseCase {
    override suspend fun getAllStocks(): List<Stock> {
        return stockRepository.loadStocks()
    }

    override suspend fun getUniqueStocks(): List<Stock> {
        return stockRepository.loadStocks()
            .groupBy { it.symbol }
            .map { Stock(it.key, it.value.first().price) }

    }

    override suspend fun getAllStocksWithPriceHistory(limit: Int): List<StockWithPriceHistory> {
        return runCatching {
            stockRepository.loadStocks().groupBy { it.symbol }
                .map {
                    val ticker = it.key
                    val prices = it.value.map { stock -> stock.price }

                    if (limit == 0) {
                        StockWithPriceHistory(ticker, prices)
                    } else {
                        StockWithPriceHistory(ticker, prices.slice(0 until limit))
                    }
                }
        }.getOrElse {
            emptyList()
        }
    }

    override suspend fun getStockWithPriceHistory(
        stockId: String,
        limit: Int
    ): List<Double>? {
        return getAllStocksWithPriceHistory(0).find { it.symbol == stockId }?.prices
    }

    override suspend fun getRealTimeStock(ticker: String): Flow<Stock> {
        val stocks = stockRepository.loadStocks().filter { it.symbol == ticker }
        return stocks.asFlow()
    }

}