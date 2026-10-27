package com.smakolyky.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smakolyky.ui.screens.favorites.FavoritesScreen
import com.smakolyky.ui.screens.home.HomeScreen
import com.smakolyky.ui.screens.recipedetails.RecipeDetailsScreen
import com.smakolyky.ui.screens.search.SearchScreen
import com.smakolyky.ui.screens.shopping.ShoppingListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onRecipeClick = { id -> navController.navigate(Screen.RecipeDetails.createRoute(id)) },
                onSearchClick = { navController.navigate(Screen.Search.route) },
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onRecipeClick = { id -> navController.navigate(Screen.RecipeDetails.createRoute(id)) },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(onRecipeClick = { id -> navController.navigate(Screen.RecipeDetails.createRoute(id)) })
        }

        composable(Screen.ShoppingList.route) {
            ShoppingListScreen()
        }

        composable(Screen.Profile.route) {
            androidx.compose.material3.Text(
                text = "Профіль (авторизація з'явиться пізніше)",
                modifier =
                    androidx.compose.ui.Modifier
                        .padding(16.dp),
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

val BOTTOM_NAV_ROUTES =
    setOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Favorites.route,
        Screen.ShoppingList.route,
        Screen.Profile.route,
    )
