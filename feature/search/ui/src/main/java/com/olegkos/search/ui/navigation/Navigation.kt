package com.olegkos.search.ui.navigation


import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.olegkos.common.navigation.FeatureApi
import com.olegkos.common.navigation.NavigationRoute
import com.olegkos.common.navigation.NavigationSubGraphRoute
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
        RecipeListScreen(
          modifier = Modifier,
          viewModel = hiltViewModel<RecipeListViewModel>(),
          onClick = {}
        )
      }

      composable(route = NavigationRoute.RecipeDetails.route) {

      }


    }


  }
}

