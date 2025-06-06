package com.olegkos.search.data.repository

import com.olegkos.search.data.local.RecipeDao
import com.olegkos.search.data.remote.SearchApiService
import com.olegkos.search.data.utils.toDomain
import com.olegkos.search.domain.model.Recipe
import com.olegkos.search.domain.model.RecipeDetail
import com.olegkos.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl
@Inject constructor(
  private val searchApiService: SearchApiService,
  private val recipeDao: RecipeDao
) : SearchRepository {
  override suspend fun getRecipes(query: String): Result<List<Recipe>> {
    return try {
      val response = searchApiService.getRecipes(query)

      if (response.isSuccessful) {
        response.body()?.meals?.let {
          Result.success(it.toDomain())
        } ?: run {
          Result.failure(Exception(response.errorBody().toString()))
        }

      } else {
        Result.failure(Exception(response.errorBody().toString()))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }

  }

  override suspend fun getRecipeDetails(id: String): Result<RecipeDetail> {
    return try {
      val response = searchApiService.getRecipeDetails(id)
      return if (response.isSuccessful) {
        response.body()?.meals?.let {
          if (it.isNotEmpty()) {
            Result.success(it.first().toDomain())
          } else {
            Result.failure(Exception("list is empty"))
          }
        } ?: run {
          Result.failure(Exception("error occurred"))
        }
      } else {
        Result.failure(Exception("error occurred"))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }


  }

  override suspend fun insertRecipe(recipe: Recipe) {
    recipeDao.insertRecipe(recipe)
  }

  override suspend fun deleteRecipe(recipe: Recipe) {
    recipeDao.deleteRecipe(recipe)
  }

  override fun getAllRecipes(): Flow<List<Recipe>> {
    return recipeDao.getAllRecipes()
  }
}