package com.smakolyky.data.repository

import com.smakolyky.data.local.dao.ShoppingDao
import com.smakolyky.data.local.entity.ShoppingItemEntity
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.ShoppingItem
import com.smakolyky.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
) : ShoppingRepository {

    override fun observeItems(): Flow<List<ShoppingItem>> =
        shoppingDao.observeAll().map { list ->
            list.map {
                ShoppingItem(
                    id = it.id,
                    ingredientName = it.ingredientName,
                    measure = it.measure,
                    recipeTitle = it.recipeTitle,
                    isChecked = it.isChecked,
                )
            }
        }

    override suspend fun addFromRecipe(recipe: Recipe) {
        val now = System.currentTimeMillis()
        shoppingDao.insertAll(
            recipe.ingredients.map { ingredient ->
                ShoppingItemEntity(
                    ingredientName = ingredient.name,
                    measure = ingredient.measure,
                    recipeId = recipe.id,
                    recipeTitle = recipe.title,
                    addedAt = now,
                )
            }
        )
    }

    override suspend fun toggleChecked(item: ShoppingItem) {
        shoppingDao.update(
            ShoppingItemEntity(
                id = item.id,
                ingredientName = item.ingredientName,
                measure = item.measure,
                recipeId = null,
                recipeTitle = item.recipeTitle,
                isChecked = !item.isChecked,
                addedAt = System.currentTimeMillis(),
            )
        )
    }

    override suspend fun clearChecked() = shoppingDao.deleteChecked()
}
