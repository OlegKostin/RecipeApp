package com.olegkos.data.di

import android.content.Context
import com.olegkos.data.local.RecipeDataBase
import com.olegkos.search.data.local.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

  @Provides
  @Singleton
  fun provideAppDatabase(@ApplicationContext context: Context) =
   RecipeDataBase.getInstance(context)

  @Provides
  fun provideRecipeDao(appDatabase: RecipeDataBase): RecipeDao {
    return appDatabase.getRecipeDao()
  }
}