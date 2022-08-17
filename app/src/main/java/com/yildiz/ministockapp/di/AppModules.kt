package com.yildiz.ministockapp.di

import com.yildiz.ministockapp.model.StockRepository
import com.yildiz.ministockapp.model.StockRepositoryImpl
import com.yildiz.ministockapp.api.ArticleApi
import com.yildiz.ministockapp.usecase.ArticleUseCase
import com.yildiz.ministockapp.usecase.ArticleUseCaseImpl
import com.yildiz.ministockapp.usecase.StockUseCase
import com.yildiz.ministockapp.usecase.StockUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    fun provideNewsApi(): ArticleApi {
        return Retrofit.Builder()
            .baseUrl("https://saurav.tech/NewsAPI/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApi::class.java)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindNewsUseCase(newsUseCaseImpl: ArticleUseCaseImpl): ArticleUseCase

    @Binds
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

    @Binds
    abstract fun bindStockUseCase(stockUseCaseImpl: StockUseCaseImpl): StockUseCase
}