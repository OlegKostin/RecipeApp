package com.olegkos.search.domain.repository

import com.olegkos.search.domain.model.Recipe
import com.olegkos.search.domain.model.RecipeDetail

interface SearchRepository {

  suspend fun getRecipes(query: String): Result<List<Recipe>>

  suspend fun getRecipeDetails(id: String): Result<RecipeDetail>
}