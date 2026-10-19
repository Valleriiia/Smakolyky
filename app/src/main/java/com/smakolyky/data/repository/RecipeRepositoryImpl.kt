package com.smakolyky.data.repository

import com.smakolyky.data.mapper.toDomain
import com.smakolyky.data.remote.MealDbApi
import com.smakolyky.domain.model.Category
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl
    @Inject
    constructor(
        private val api: MealDbApi,
    ) : RecipeRepository {
        override fun searchRecipes(query: String): Flow<List<Recipe>> =
            flow {
                val response = api.searchMeals(query)
                emit(response.meals.orEmpty().map { it.toDomain() })
            }

        override fun getRecipe(id: String): Flow<Recipe?> =
            flow {
                val response = api.lookupMeal(id)
                emit(
                    response.meals
                        .orEmpty()
                        .firstOrNull()
                        ?.toDomain(),
                )
            }

        override fun getCategories(): Flow<List<Category>> =
            flow {
                val response = api.getCategories()
                emit(response.categories.orEmpty().map { it.toDomain() })
            }
    }
