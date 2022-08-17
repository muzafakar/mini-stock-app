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
    private val _article = MutableStateFlow<List<List<Article>>>(emptyList())
    val article: StateFlow<List<List<Article>>> = _article

    fun loadGroupedArticles() {
        viewModelScope.launch {
            _article.value = newsUseCse.getArticles().chunked(6)
        }
    }
}