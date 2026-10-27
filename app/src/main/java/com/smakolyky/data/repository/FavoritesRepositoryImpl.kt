package com.smakolyky.data.repository

import com.smakolyky.data.local.dao.FavoriteDao
import com.smakolyky.data.local.dao.RecipeDao
import com.smakolyky.data.local.entity.FavoriteEntity
import com.smakolyky.data.local.entity.toDomain
import com.smakolyky.data.local.entity.toEntity
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Поки без сервера — локально; синхронізація з BackendApi додасться разом з авторизацією
@Singleton
class FavoritesRepositoryImpl
    @Inject
    constructor(
        private val favoriteDao: FavoriteDao,
        private val recipeDao: RecipeDao,
    ) : FavoritesRepository {
        override fun observeFavorites(): Flow<List<Recipe>> =
            favoriteDao.observeFavoriteRecipes().map { list ->
                list.map { it.toDomain() }
            }

        override fun isFavorite(recipeId: String): Flow<Boolean> = favoriteDao.isFavorite(recipeId)

        override suspend fun toggleFavorite(recipe: Recipe) {
            val alreadyFavorite = favoriteDao.isFavorite(recipe.id).first()
            if (alreadyFavorite) {
                favoriteDao.delete(recipe.id)
            } else {
                recipeDao.upsert(recipe.toEntity())
                favoriteDao.insert(FavoriteEntity(recipeId = recipe.id, addedAt = System.currentTimeMillis()))
            }
        }
    }
