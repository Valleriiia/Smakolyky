package com.smakolyky.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.smakolyky.navigation.Screen

private data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
)

private val ITEMS =
    listOf(
        BottomNavItem(Screen.Home, "Головна", Icons.Default.Home),
        BottomNavItem(Screen.Search, "Пошук", Icons.Default.Search),
        BottomNavItem(Screen.Favorites, "Улюблені", Icons.Default.Favorite),
        BottomNavItem(Screen.ShoppingList, "Покупки", Icons.Default.ShoppingCart),
        BottomNavItem(Screen.Profile, "Профіль", Icons.Default.Person),
    )

@Composable
fun BottomNavBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    NavigationBar {
        ITEMS.forEach { item ->
            val selected = currentRoute?.hierarchy?.any { it.route == item.screen.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
            )
        }
    }
}
