package com.yildiz.ministockapp.usecase

import com.yildiz.ministockapp.api.NewsApi
import com.yildiz.ministockapp.model.News
import javax.inject.Inject

interface NewsUseCase {
    suspend fun getNews(): News
}

class NewsUseCaseImpl @Inject constructor(private val newsApi: NewsApi) : NewsUseCase {
    override suspend fun getNews(): News {
        return newsApi.getNews()
    }

}