package com.olegkos.search.domain.model

data class RecipeDetail(
  val idMeal: String,
  val strArea: String,
  val strMeal: String,
  val strMealThumb: String,
  val strCategory: String,
  val strTags: String,
  val strYoutube: String,
  val strInstructions: String,
  val ingredientsPair: List<Pair<String, String>>
)