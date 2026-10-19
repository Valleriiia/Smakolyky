package com.smakolyky.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smakolyky.ui.screens.home.HomeScreen
import com.smakolyky.ui.screens.recipedetails.RecipeDetailsScreen
import com.smakolyky.ui.screens.search.SearchScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.RecipeDetails.createRoute(recipeId))
                },
                onSearchClick = { navController.navigate(Screen.Search.route) },
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.RecipeDetails.createRoute(recipeId))
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Screen.RecipeDetails.route,
            arguments = listOf(navArgument(Screen.RecipeDetails.ARG_RECIPE_ID) { type = NavType.StringType }),
        ) {
            RecipeDetailsScreen(onBack = { navController.popBackStack() })
        }
    }
}
