package com.yildiz.ministockapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yildiz.ministockapp.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCse: NewsUseCase
) : ViewModel() {
    private val _article = MutableStateFlow("")
    val article: StateFlow<String> = _article

    fun getFirstNews() {
        viewModelScope.launch {
            _article.value = newsUseCse.getNews().articles.random(Random(Date().time)).title
        }
    }
}