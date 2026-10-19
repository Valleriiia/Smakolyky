package com.smakolyky.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Favorites : Screen("favorites")
    data object ShoppingList : Screen("shopping_list")
    data object Profile : Screen("profile")

    data object RecipeDetails : Screen("recipe/{recipeId}") {
        fun createRoute(recipeId: String) = "recipe/$recipeId"
        const val ARG_RECIPE_ID = "recipeId"
    }
}
