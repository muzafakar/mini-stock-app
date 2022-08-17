package com.yildiz.ministockapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yildiz.ministockapp.model.Article
import com.yildiz.ministockapp.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsUseCse: ArticleUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun loadGroupedArticles() {
        viewModelScope.launch {
            val data = newsUseCse.getArticles()
            _uiState.value = UiState.DataLoaded(data)
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class DataLoaded(val data: List<Article>) : UiState()
    }
}