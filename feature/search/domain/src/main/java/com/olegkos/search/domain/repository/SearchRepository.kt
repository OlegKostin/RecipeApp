package com.olegkos.search.domain.repository

import com.olegkos.search.domain.model.Recipe
import com.olegkos.search.domain.model.RecipeDetail
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

  suspend fun getRecipes(query: String): Result<List<Recipe>>

  suspend fun getRecipeDetails(id: String): Result<RecipeDetail>

  suspend fun insertRecipe(recipe: Recipe)

  suspend fun deleteRecipe(recipe: Recipe)

  fun getAllRecipes(): Flow<List<Recipe>>
}