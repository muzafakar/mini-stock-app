package com.yildiz.ministockapp.usecase

import com.yildiz.ministockapp.StockRepository
import com.yildiz.ministockapp.model.Ticker
import com.yildiz.ministockapp.model.TickerWithPriceHistory
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
            Ticker("TSLA", 123.123),
            Ticker("GOOG", 123.123),
            Ticker("NVDA", 123.123),
            Ticker("AAPL", 123.123)
        )

        val uniqueStocks = stockUseCase.getUniqueStocks()

        assertEquals(expected, uniqueStocks)
    }

    @Test
    fun `get stocks with history should return unique stocks with their price histories`() =
        runTest {
            stubAllStock()
            val expected = listOf(
                TickerWithPriceHistory("TSLA", listOf(123.123, 456.123, 789.123)),
                TickerWithPriceHistory("GOOG", listOf(123.123, 456.123, 789.123)),
                TickerWithPriceHistory("NVDA", listOf(123.123, 456.123, 789.123)),
                TickerWithPriceHistory("AAPL", listOf(123.123, 456.123, 789.123))
            )

            val stockWithPriceHistory = stockUseCase.getAllStocksWithHistory(3)

            assertEquals(expected, stockWithPriceHistory)
        }

    private fun stubAllStock() {
        coEvery { stockRepository.loadStocks() } returns dummyStocks
    }

    private val dummyStocks = listOf(
        Ticker("TSLA", 123.123),
        Ticker("GOOG", 123.123),
        Ticker("NVDA", 123.123),
        Ticker("AAPL", 123.123),
        Ticker("TSLA", 456.123),
        Ticker("GOOG", 456.123),
        Ticker("NVDA", 456.123),
        Ticker("AAPL", 456.123),
        Ticker("TSLA", 789.123),
        Ticker("GOOG", 789.123),
        Ticker("NVDA", 789.123),
        Ticker("AAPL", 789.123)
    )
}