package com.olegkos.search.domain.use_cases

import com.olegkos.search.domain.model.Recipe
import com.olegkos.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertRecipeUseCase @Inject constructor(private val searchRepository: SearchRepository) {

  operator fun invoke(recipe: Recipe) = flow<Unit> {
    searchRepository.insertRecipe(recipe)
  }.flowOn(Dispatchers.IO)

}