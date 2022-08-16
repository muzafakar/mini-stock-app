package com.yildiz.ministockapp.api

import com.yildiz.ministockapp.model.News
import retrofit2.http.GET

interface NewsApi {
    @GET("everything/cnn.json")
    suspend fun getNews(): News
}