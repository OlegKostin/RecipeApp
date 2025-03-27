package com.olegkos.search.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olegkos.common.utils.NetworkResult
import com.olegkos.common.utils.UiText
import com.olegkos.search.domain.model.Recipe
import com.olegkos.search.domain.model.RecipeDetail
import com.olegkos.search.domain.use_cases.DeleteRecipeUseCase
import com.olegkos.search.domain.use_cases.GetRecipeDetailsUseCase
import com.olegkos.search.domain.use_cases.InsertRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
  private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
  private val deleteRecipeUseCase: DeleteRecipeUseCase,
  private val insertRecipeUseCase: InsertRecipeUseCase,
) : ViewModel() {
  private val _uiState =
    MutableStateFlow(RecipeDetails.UiState())
  val uiState: StateFlow<RecipeDetails.UiState>
    get() = _uiState.asStateFlow()

  private val _navigation = Channel<RecipeDetails.Navigation>()
  val navigation: Flow<RecipeDetails.Navigation> = _navigation.receiveAsFlow()


  fun onEvent(event: RecipeDetails.Event) {
    when (event) {
      is RecipeDetails.Event.FetchRecipeDetails -> {
        recipeDetails(event.id)
      }

      RecipeDetails.Event.GoToRecipeListScreen ->
        viewModelScope.launch {
          _navigation.send(RecipeDetails.Navigation.GoToRecipeListScreen)
        }

      is RecipeDetails.Event.DeleteRecipe -> {
        deleteRecipeUseCase(event.recipeDetails.toRecipe()).launchIn(viewModelScope)

      }

      is RecipeDetails.Event.InsertRecipe -> {
        insertRecipeUseCase(event.recipeDetails.toRecipe()).launchIn(viewModelScope)

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
          _uiState.update {
            RecipeDetails.UiState(data = result.data)
          }
        }
      }
    }.launchIn(viewModelScope)

  private fun RecipeDetail.toRecipe(): Recipe {
    return Recipe(
      idMeal,
      strArea,
      strMeal,
      strMealThumb,
      strCategory,
      strTags,
      strYoutube,
      strInstructions
    )
  }

}

object RecipeDetails {
  data class UiState(
    val isLoading: Boolean = false,
    val error: UiText = UiText.Idle,
    val data: RecipeDetail? = null
  )

  sealed interface Navigation {
    data object GoToRecipeListScreen : Navigation

  }

  sealed interface Event {

    data class FetchRecipeDetails(val id: String) : Event

    data object GoToRecipeListScreen : Event

    data class InsertRecipe(val recipeDetails: RecipeDetail) : Event

    data class DeleteRecipe(val recipeDetails: RecipeDetail) : Event
  }

}
