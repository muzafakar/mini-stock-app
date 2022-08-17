package com.yildiz.ministockapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yildiz.ministockapp.model.StockWithPriceHistory
import com.yildiz.ministockapp.usecase.StockUseCase
import com.yildiz.ministockapp.util.Const.MAX_VISIBLE_PRICE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val stockUseCase: StockUseCase
) : ViewModel() {
    private val _stocks = MutableStateFlow<List<StockWithPriceHistory>>(emptyList())
    val stocks: StateFlow<List<StockWithPriceHistory>> = _stocks

    private val _realtimeStock = MutableStateFlow<StockWithPriceHistory?>(null)
    val realtimeStock: StateFlow<StockWithPriceHistory?> = _realtimeStock

    fun loadStock() {
        viewModelScope.launch {
            val allStocks = stockUseCase.getAllStocksWithPriceHistory(MAX_VISIBLE_PRICE)
            _stocks.value = allStocks
        }
    }

    fun observeStockPrice(stockId: String) {
        viewModelScope.launch {
            val prices = stockUseCase.getStockWithPriceHistory(stockId, 0) ?: return@launch
            _realtimeStock.value = StockWithPriceHistory(stockId, emptyList())

            prices.forEach {
                delay(2000)
                val currentPrices = realtimeStock.value?.prices ?: emptyList()
                _realtimeStock.value = realtimeStock.value?.copy(prices = currentPrices.plus(it))
            }
        }
    }
}
