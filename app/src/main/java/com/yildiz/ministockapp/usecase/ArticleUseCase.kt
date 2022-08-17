package com.yildiz.ministockapp.usecase

import com.yildiz.ministockapp.api.ArticleApi
import com.yildiz.ministockapp.model.Article
import javax.inject.Inject

interface ArticleUseCase {
    suspend fun getArticles(): List<Article>
}

class ArticleUseCaseImpl @Inject constructor(private val articleApi: ArticleApi) : ArticleUseCase {
    override suspend fun getArticles(): List<Article> {
        return articleApi.getArticles().body()?.articles ?: emptyList()
    }

}