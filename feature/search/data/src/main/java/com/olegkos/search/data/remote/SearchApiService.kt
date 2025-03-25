package com.olegkos.search.data.remote

import com.olegkos.search.data.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
  //https://www.themealdb.com/api/json/v1/1/search.php?s=chicken
  @GET("search.php")
  suspend fun getRecipes(
    @Query("s") query: String
  ): Response<RecipeResponse>


  @GET("lookup.php?")
  suspend fun getRecipeDetails(
    @Query("i") info: String
  ): Response<RecipeResponse>
}