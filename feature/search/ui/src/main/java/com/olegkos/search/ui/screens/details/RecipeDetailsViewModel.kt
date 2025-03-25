package com.olegkos.search.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olegkos.common.utils.NetworkResult
import com.olegkos.common.utils.UiText
import com.olegkos.search.domain.model.RecipeDetail
import com.olegkos.search.domain.use_cases.GetRecipeDetailsUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class RecipeDetailsViewModel @Inject constructor(
  private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase
) : ViewModel() {
  private val _uiState =
    MutableStateFlow(RecipeDetails.UiState())
  val uiState: StateFlow<RecipeDetails.UiState>
    get() = _uiState.asStateFlow()

  fun onEvent(event: RecipeDetails.Event) {
    when (event) {
      is RecipeDetails.Event.FetchRecipeDetails -> {
        recipeDetails(event.id)
      }
    }
  }

  private fun recipeDetails(info: String) = getRecipeDetailsUseCase(info)
    .onEach { result ->
      when (result) {
        is NetworkResult.Error -> {
          _uiState.update {
            RecipeDetails.UiState(error = UiText.RemoteString(result.message.toString()))
          }
        }

        is NetworkResult.Loading -> {
          _uiState.update {
            RecipeDetails.UiState(isLoading = true)
          }
        }

        is NetworkResult.Success -> {
          RecipeDetails.UiState(data = result.data)
        }
      }
    }.launchIn(viewModelScope)


}

object RecipeDetails {
  data class UiState(
    val isLoading: Boolean = false,
    val error: UiText = UiText.Idle,
    val data: RecipeDetail? = null
  )

  sealed interface Navigation {


  }

  sealed interface Event {

    data class FetchRecipeDetails(val id: String) : Event

  }

}