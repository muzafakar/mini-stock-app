package com.yildiz.ministockapp.viewModel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yildiz.ministockapp.model.Ticker
import com.yildiz.ministockapp.model.TickerWithPriceHistory
import com.yildiz.ministockapp.usecase.StockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val stockUseCase: StockUseCase
) : ViewModel() {
    private val _stocks = MutableStateFlow<List<TickerWithPriceHistory>>(emptyList())
    val stocks: StateFlow<List<TickerWithPriceHistory>> = _stocks

    fun loadStock() {
        viewModelScope.launch {
            val allStocks = stockUseCase.getAllStocksWithHistory(10)
            _stocks.value = allStocks
        }
    }
}
