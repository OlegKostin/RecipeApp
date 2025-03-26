package com.olegkos.search.data.di

import com.olegkos.search.data.remote.SearchApiService
import com.olegkos.search.data.repository.SearchRepositoryImpl
import com.olegkos.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

@Module
@InstallIn(SingletonComponent::class)
object SearchDataModule {

  @Provides
  @Singleton
  fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  fun providesSearchApiService(retrofit: Retrofit): SearchApiService =
    retrofit.create(SearchApiService::class.java)

  @Provides
  fun provideSearchRepo(searchApiService: SearchApiService,
                        ): SearchRepository {
    return SearchRepositoryImpl(searchApiService)
  }

}