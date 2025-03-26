package com.olegkos.search.domain.use_cases

import android.util.Log
import com.olegkos.common.utils.NetworkResult
import com.olegkos.search.domain.model.Recipe
import com.olegkos.search.domain.repository.SearchRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllRecipeUseCase @Inject constructor(
  private val searchRepository: SearchRepository
) {

  operator fun invoke(query: String) = flow<NetworkResult<List<Recipe>>> {
    emit(NetworkResult.Loading())
    val response = searchRepository.getRecipes(query)
    when {
      response.isSuccess -> {
        emit(NetworkResult.Success(data = response.getOrThrow()))
      }
      response.isFailure -> emit(
        NetworkResult.Error(
          message = response.exceptionOrNull()?.localizedMessage ?: "getall"
        )
      )
    }
  }.catch {
    emit(NetworkResult.Error(it.message.toString()))
  }.flowOn(Dispatchers.IO)
}
