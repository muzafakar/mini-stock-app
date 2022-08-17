package com.yildiz.ministockapp.usecase

import com.yildiz.ministockapp.model.StockRepository
import com.yildiz.ministockapp.model.Stock
import com.yildiz.ministockapp.model.StockWithPriceHistory
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StockUseCaseImplTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var stockRepository: StockRepository
    lateinit var stockUseCase: StockUseCase

    @Before
    fun setUp() {
        stockUseCase = StockUseCaseImpl(stockRepository)
    }

    @Test
    fun `get all stocks should return all stocks`() = runTest {
        stubAllStock()

        val allStocks = stockUseCase.getAllStocks()

        assertEquals(allStocks, dummyStocks)
    }

    @Test
    fun `get unique stocks should return unique stocks with first price`() = runTest {
        stubAllStock()
        val expected = listOf(
            Stock("TSLA", 123.123),
            Stock("GOOG", 123.123),
            Stock("NVDA", 123.123),
            Stock("AAPL", 123.123)
        )

        val uniqueStocks = stockUseCase.getUniqueStocks()

        assertEquals(expected, uniqueStocks)
    }

    @Test
    fun `get stocks with history should return unique stocks with their price histories`() =
        runTest {
            stubAllStock()
            val expected = listOf(
                StockWithPriceHistory("TSLA", listOf(123.123, 456.123, 789.123)),
                StockWithPriceHistory("GOOG", listOf(123.123, 456.123, 789.123)),
                StockWithPriceHistory("NVDA", listOf(123.123, 456.123, 789.123)),
                StockWithPriceHistory("AAPL", listOf(123.123, 456.123, 789.123))
            )

            val stockWithPriceHistory = stockUseCase.getAllStocksWithPriceHistory(3)

            assertEquals(expected, stockWithPriceHistory)
        }

    private fun stubAllStock() {
        coEvery { stockRepository.loadStocks() } returns dummyStocks
    }

    private val dummyStocks = listOf(
        Stock("TSLA", 123.123),
        Stock("GOOG", 123.123),
        Stock("NVDA", 123.123),
        Stock("AAPL", 123.123),
        Stock("TSLA", 456.123),
        Stock("GOOG", 456.123),
        Stock("NVDA", 456.123),
        Stock("AAPL", 456.123),
        Stock("TSLA", 789.123),
        Stock("GOOG", 789.123),
        Stock("NVDA", 789.123),
        Stock("AAPL", 789.123)
    )
}