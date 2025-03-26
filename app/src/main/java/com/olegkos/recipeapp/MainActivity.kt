package com.olegkos.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.olegkos.navigation.NavigationSubGraphs
import com.olegkos.navigation.RecipeNavigation
import com.olegkos.recipeapp.ui.theme.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var navigationSubGraphs: NavigationSubGraphs

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      RecipeAppTheme {
        Surface(modifier = Modifier.safeContentPadding()) {
          RecipeNavigation(navigationSubGraphs = navigationSubGraphs)
        }
      }
    }
  }
}

