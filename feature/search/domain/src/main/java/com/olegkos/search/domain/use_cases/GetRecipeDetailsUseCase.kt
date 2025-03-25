package com.olegkos.search.domain.use_cases

import com.olegkos.common.utils.NetworkResult
import com.olegkos.search.domain.model.RecipeDetails
import com.olegkos.search.domain.repository.SearchRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRecipeDetailsUseCase @Inject constructor(
  private val searchRepository: SearchRepository
) {

  operator fun invoke(info: String) = flow<NetworkResult<RecipeDetails>> {
    emit(NetworkResult.Loading())
    val response = searchRepository.getRecipeDetails(id = info)
    when {
      response.isSuccess -> emit(NetworkResult.Success(data = response.getOrThrow()))
      response.isFailure -> emit(
        NetworkResult.Error(
          message = response.exceptionOrNull()?.localizedMessage ?: "getdetails"
        )
      )
    }
  }.catch {
    emit(NetworkResult.Error(message = it.localizedMessage))
  }.flowOn(Dispatchers.IO)
}