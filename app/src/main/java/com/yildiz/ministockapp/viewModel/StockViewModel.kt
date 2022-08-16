package com.yildiz.ministockapp.viewModel

import androidx.lifecycle.ViewModel
import com.yildiz.ministockapp.usecase.StockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val stockUseCase: StockUseCase
) : ViewModel() {


}
