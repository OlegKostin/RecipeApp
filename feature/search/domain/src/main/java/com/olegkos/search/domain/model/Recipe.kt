package com.olegkos.search.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
  @PrimaryKey(autoGenerate = false)
  val idMeal: String,
  val strArea: String,
  val strMeal: String,
  val strMealThumb: String,
  val strCategory: String,
  val strTags: String,
  val strYoutube: String,
  val strInstructions: String,
)