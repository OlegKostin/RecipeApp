package com.olegkos.search.ui.navigation


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.olegkos.common.navigation.FeatureApi
import com.olegkos.common.navigation.NavigationRoute
import com.olegkos.common.navigation.NavigationSubGraphRoute
import com.olegkos.search.ui.screens.details.RecipeDetails
import com.olegkos.search.ui.screens.details.RecipeDetailsScreen
import com.olegkos.search.ui.screens.details.RecipeDetailsViewModel
import com.olegkos.search.ui.screens.recipe_list.RecipeList
import com.olegkos.search.ui.screens.recipe_list.RecipeListScreen
import com.olegkos.search.ui.screens.recipe_list.RecipeListViewModel

interface SearchFeatureApi : FeatureApi


class SearchFeatureApiImpl : SearchFeatureApi {
  override fun registerGraph(
    navGraphBuilder: NavGraphBuilder,
    navHostController: NavHostController
  ) {
    navGraphBuilder.navigation(
      route = NavigationSubGraphRoute.Search.route,
      startDestination = NavigationRoute.RecipeList.route
    ) {

      composable(route = NavigationRoute.RecipeList.route) {
        val viewmodel = hiltViewModel<RecipeListViewModel>()
        RecipeListScreen(
          modifier = Modifier,
          viewModel = viewmodel,
          navHostController = navHostController,
          onClick = {
            viewmodel.onEvent(RecipeList.Event.GoToRecipeDetails(it))
          }
        )
      }

      composable(route = NavigationRoute.RecipeDetails.route) {
        val viewModel = hiltViewModel<RecipeDetailsViewModel>()
        val mealId = it.arguments?.getString("id")
        LaunchedEffect(key1 = mealId) {
          mealId?.let {
            viewModel.onEvent(RecipeDetails.Event.FetchRecipeDetails(it))
          }
        }
        RecipeDetailsScreen(

          modifier = Modifier,
          viewModel = viewModel
        )
      }


    }


  }
}

