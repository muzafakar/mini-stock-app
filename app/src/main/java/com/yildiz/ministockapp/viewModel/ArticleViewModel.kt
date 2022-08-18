package com.yildiz.ministockapp.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yildiz.ministockapp.model.Article
import com.yildiz.ministockapp.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsUseCse: ArticleUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun loadGroupedArticles() {
        viewModelScope.launch {
            val data = newsUseCse.getArticles()
            _uiState.value = UiState.DataLoaded(data)
        }
    }

    fun openArticleInBrowser(article: Article) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
        context.startActivity(browserIntent)
    }

    sealed class UiState {
        object Loading : UiState()
        data class DataLoaded(val data: List<Article>) : UiState()
    }
}