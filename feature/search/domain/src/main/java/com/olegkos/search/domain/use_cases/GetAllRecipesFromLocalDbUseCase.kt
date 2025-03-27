package com.olegkos.search.domain.use_cases

import com.olegkos.search.domain.repository.SearchRepository
import javax.inject.Inject


class GetAllRecipesFromLocalDbUseCase @Inject constructor(private val searchRepository: SearchRepository) {

  operator fun invoke() = searchRepository.getAllRecipes()
}