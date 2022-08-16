package com.yildiz.ministockapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yildiz.ministockapp.model.Article
import com.yildiz.ministockapp.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCse: NewsUseCase
) : ViewModel() {
    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article

    fun getFirstNews() {
        viewModelScope.launch {
            _article.value = newsUseCse.getNews().articles.first()
        }
    }
}