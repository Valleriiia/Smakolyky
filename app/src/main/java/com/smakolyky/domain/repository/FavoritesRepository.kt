package com.smakolyky.domain.repository

import com.smakolyky.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun observeFavorites(): Flow<List<Recipe>>
    fun isFavorite(recipeId: String): Flow<Boolean>
    suspend fun toggleFavorite(recipe: Recipe)
}
