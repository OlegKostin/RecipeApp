package com.olegkos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.olegkos.search.data.local.RecipeDao
import com.olegkos.search.domain.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RecipeDataBase : RoomDatabase() {
  companion object {
    fun getInstance(context: Context) =
      Room.databaseBuilder(context, RecipeDataBase::class.java, "recipe_app_db")
        .fallbackToDestructiveMigration()
        .build()
  }

  abstract fun getRecipeDao(): RecipeDao
}