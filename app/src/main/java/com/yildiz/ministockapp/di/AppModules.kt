package com.yildiz.ministockapp.di

import com.yildiz.ministockapp.api.NewsApi
import com.yildiz.ministockapp.usecase.NewsUseCase
import com.yildiz.ministockapp.usecase.NewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl("https://saurav.tech/NewsAPI/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindNewsUseCase(newsUseCaseImpl: NewsUseCaseImpl): NewsUseCase
}