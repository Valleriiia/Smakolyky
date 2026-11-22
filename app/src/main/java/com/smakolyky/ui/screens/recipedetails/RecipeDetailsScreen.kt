package com.smakolyky.ui.screens.recipedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.smakolyky.common.UiState
import com.smakolyky.util.YoutubeUtil

private val TABS = listOf("Інгредієнти", "Кроки", "Відео")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    onBack: () -> Unit,
    viewModel: RecipeDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text((state as? UiState.Success)?.data?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::onFavoriteClick) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Улюблене",
                        )
                    }
                },
            )
        },
    ) { padding ->
        when (val s = state) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.padding(padding).fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Text(s.message, modifier = Modifier.padding(padding).padding(16.dp))
            }

            is UiState.Success -> {
                val recipe = s.data
                Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                    AsyncImage(
                        model = recipe.imageUrl,
                        contentDescription = recipe.title,
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                    )
                    Button(
                        onClick = { viewModel.addToShoppingList() },
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                    ) { Text("Додати інгредієнти у список покупок") }

                    TabRow(selectedTabIndex = selectedTab) {
                        TABS.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = { Text(title) },
                            )
                        }
                    }

                    when (selectedTab) {
                        0 -> {
                            LazyColumn {
                                items(recipe.ingredients) { ingredient ->
                                    Text(
                                        "• ${ingredient.name} — ${ingredient.measure}",
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                                    )
                                }
                            }
                        }

                        1 -> {
                            LazyColumn {
                                items(recipe.steps) { step ->
                                    Text(
                                        "${step.stepNumber}. ${step.description}",
                                        modifier = Modifier.padding(16.dp),
                                    )
                                }
                            }
                        }

                        else -> {
                            val videoUrl = recipe.videoUrl
                            if (videoUrl == null) {
                                Text("Для цього рецепта немає відео", modifier = Modifier.padding(16.dp))
                            } else {
                                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                    AsyncImage(
                                        model = YoutubeUtil.thumbnailUrl(videoUrl),
                                        contentDescription = "Прев'ю відео",
                                        modifier = Modifier.fillMaxWidth().height(200.dp),
                                    )
                                    Button(
                                        onClick = { YoutubeUtil.openVideo(context, videoUrl) },
                                        modifier = Modifier.align(Alignment.Center),
                                    ) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                                        Text(" Дивитись на YouTube")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
