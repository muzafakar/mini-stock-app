package com.yildiz.ministockapp.usecase

import com.yildiz.ministockapp.StockRepository
import com.yildiz.ministockapp.model.Stock
import com.yildiz.ministockapp.model.StockWithHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

interface StockUseCase {
    fun getAllStockWithHistory(): List<StockWithHistory>
    fun getRealTimeStock(ticker: String): Flow<Stock>
}

class StockUseCaseImpl @Inject constructor(
    private val stockRepository: StockRepository
) : StockUseCase {
    override fun getAllStockWithHistory(): List<StockWithHistory> {
        return runCatching {
            stockRepository.stocks.groupBy { it.ticker }
                .map {
                    val ticker = it.key
                    val prices = it.value.map { stock -> stock.price }
                    StockWithHistory(ticker, prices)
                }
        }.getOrElse {
            emptyList()
        }
    }

    override fun getRealTimeStock(ticker: String): Flow<Stock> {
        val stocks = stockRepository.stocks.filter { it.ticker == ticker }
        return stocks.asFlow()
    }

}