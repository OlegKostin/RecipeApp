package com.olegkos.search.data.repository

import com.olegkos.search.data.remote.SearchApiService
import com.olegkos.search.data.utils.toDomain
import com.olegkos.search.domain.model.Recipe
import com.olegkos.search.domain.model.RecipeDetails
import com.olegkos.search.domain.repository.SearchRepository
import jakarta.inject.Inject

class SearchRepositoryImpl
@Inject constructor(private val searchApiService: SearchApiService) : SearchRepository {
  override suspend fun getRecipes(query: String): Result<List<Recipe>> {
    val response = searchApiService.getRecipes(query)
    return if (response.isSuccessful) {
      response.body()?.meals?.let {
        Result.success(it.toDomain())
      } ?: run {
        Result.failure(Exception(response.errorBody().toString()))
      }

    } else {
      Result.failure(Exception(response.errorBody().toString()))
    }

  }

  override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
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


  }
}