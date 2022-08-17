package com.yildiz.ministockapp.api

import com.yildiz.ministockapp.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET

interface ArticleApi {
    @GET("everything/cnn.json")
    suspend fun getArticles(): Response<ArticleResponse>
}