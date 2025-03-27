package com.olegkos.di

import com.olegkos.navigation.NavigationSubGraphs
import com.olegkos.search.ui.navigation.SearchFeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  fun provideNavigationSubGraphs(
    searchFeatureApi: SearchFeatureApi,
  ): NavigationSubGraphs {
    return NavigationSubGraphs(searchFeatureApi)
  }
}