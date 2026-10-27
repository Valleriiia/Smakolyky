package com.smakolyky.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smakolyky.ui.components.RecipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onRecipeClick: (String) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Улюблені") }) }) { padding ->
        if (favorites.isEmpty()) {
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                Text(
                    text = "Поки що тут порожньо — додай рецепт у улюблені на його сторінці",
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(favorites, key = { it.id }) { recipe ->
                    RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
                }
            }
        }
    }
}
