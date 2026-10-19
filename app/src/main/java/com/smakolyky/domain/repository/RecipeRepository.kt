package com.smakolyky.domain.repository

import com.smakolyky.domain.model.Category
import com.smakolyky.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun searchRecipes(query: String): Flow<List<Recipe>>

    fun getRecipe(id: String): Flow<Recipe?>

    fun getCategories(): Flow<List<Category>>
}
