package com.smakolyky.domain.repository

import com.smakolyky.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

data class ShoppingItem(
    val id: Long,
    val ingredientName: String,
    val measure: String,
    val recipeTitle: String?,
    val isChecked: Boolean,
)

interface ShoppingRepository {
    fun observeItems(): Flow<List<ShoppingItem>>
    suspend fun addFromRecipe(recipe: Recipe)
    suspend fun toggleChecked(item: ShoppingItem)
    suspend fun clearChecked()
}
