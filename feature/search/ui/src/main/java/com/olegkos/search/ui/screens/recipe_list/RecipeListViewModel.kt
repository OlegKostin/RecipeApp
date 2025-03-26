package com.olegkos.search.ui.screens.recipe_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olegkos.common.utils.NetworkResult
import com.olegkos.common.utils.UiText
import com.olegkos.search.domain.model.Recipe
import com.olegkos.search.domain.use_cases.GetAllRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
  private val getAllRecipeUseCase: GetAllRecipeUseCase
) : ViewModel() {

  private val _uiState = MutableStateFlow(RecipeList.UiState())
  val uiState: StateFlow<RecipeList.UiState>
    get() = _uiState.asStateFlow()

  private fun search(query: String) =
    getAllRecipeUseCase.invoke(query)
      .onEach { result ->
        when (result) {
          is NetworkResult.Error -> {
            _uiState.update {
              RecipeList.UiState(error = UiText.RemoteString(result.message.toString()))
            }
          }

          is NetworkResult.Loading -> {
            _uiState.update {
              RecipeList.UiState(isLoading = true)
            }
          }

          is NetworkResult.Success -> {
            _uiState.update {
              RecipeList.UiState(data = result.data)
            }
          }
        }

      }.launchIn(viewModelScope)


  fun onEvent(event: RecipeList.Event) {

    when (event) {

      is RecipeList.Event.SearchRecipe -> {
        search(event.q)
      }
    }
  }
}

object RecipeList {

  data class UiState(
    val isLoading: Boolean = false,
    val error: UiText = UiText.Idle,
    val data: List<Recipe>? = null
  )

  sealed interface Navigation {


  }

  sealed interface Event {
    data class SearchRecipe(val q: String) : Event


  }

}