package com.smakolyky.data.repository

import com.smakolyky.data.local.dao.RecipeDao
import com.smakolyky.data.local.entity.toDomain
import com.smakolyky.data.local.entity.toEntity
import com.smakolyky.data.mapper.toDomain
import com.smakolyky.data.remote.MealDbApi
import com.smakolyky.domain.model.Category
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl
    @Inject
    constructor(
        private val api: MealDbApi,
        private val recipeDao: RecipeDao,
    ) : RecipeRepository {
        override fun searchRecipes(query: String): Flow<List<Recipe>> =
            flow {
                try {
                    val response = api.searchMeals(query)
                    val recipes = response.meals.orEmpty().map { it.toDomain() }
                    recipeDao.upsertAll(recipes.map { it.toEntity() })
                    emit(recipes)
                } catch (e: Exception) {
                    val cached = recipeDao.searchByTitle(query).first().map { it.toDomain() }
                    if (cached.isEmpty()) throw e
                    emit(cached)
                }
            }

        override fun getRecipe(id: String): Flow<Recipe?> =
            flow {
                val cached = recipeDao.getById(id)?.toDomain()

                if (cached != null && cached.isDetailLoaded) {
                    emit(cached)
                    return@flow
                }

                try {
                    val response = api.lookupMeal(id)
                    val recipe =
                        response.meals
                            .orEmpty()
                            .firstOrNull()
                            ?.toDomain()
                    if (recipe != null) {
                        recipeDao.upsert(recipe.toEntity())
                        emit(recipe)
                    } else {
                        emit(cached)
                    }
                } catch (e: Exception) {
                    if (cached != null) emit(cached) else throw e
                }
            }

        override fun getCategories(): Flow<List<Category>> =
            flow {
                val response = api.getCategories()
                emit(response.categories.orEmpty().map { it.toDomain() })
            }
    }
