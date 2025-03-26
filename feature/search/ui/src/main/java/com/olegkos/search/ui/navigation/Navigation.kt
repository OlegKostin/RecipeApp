package com.olegkos.search.ui.navigation


import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.olegkos.common.navigation.FeatureApi
import com.olegkos.common.navigation.NavigationRoute
import com.olegkos.common.navigation.NavigationSubGraphRoute

interface SearchFeatureApi : FeatureApi


class SearchFeatureApiImpl : SearchFeatureApi {
  override fun registerGraph(
    navGraphBuilder: androidx.navigation.NavGraphBuilder,
    navHostController: androidx.navigation.NavHostController
  ) {
    navGraphBuilder.navigation(
      route = NavigationSubGraphRoute.Search.route,
      startDestination = NavigationRoute.RecipeList.route
    ) {

      composable(route = NavigationRoute.RecipeList.route) {

      }

      composable(route = NavigationRoute.RecipeDetails.route) {

      }


    }


  }
}

