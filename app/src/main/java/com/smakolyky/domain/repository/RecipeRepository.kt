package com.smakolyky.domain.repository

import com.smakolyky.domain.model.Area
import com.smakolyky.domain.model.Category
import com.smakolyky.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun searchRecipes(query: String): Flow<List<Recipe>>
    fun getRecipe(id: String): Flow<Recipe?>
    fun getCategories(): Flow<List<Category>>
    fun getAreas(): Flow<List<Area>>
    fun getRandomRecipe(): Flow<Recipe>
    fun getByCategory(name: String): Flow<List<Recipe>>
    fun getByArea(name: String): Flow<List<Recipe>>
}
