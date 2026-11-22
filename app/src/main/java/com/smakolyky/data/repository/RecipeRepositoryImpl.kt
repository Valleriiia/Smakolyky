package com.smakolyky.data.repository

import com.smakolyky.data.local.dao.RecipeDao
import com.smakolyky.data.local.entity.toDomain
import com.smakolyky.data.local.entity.toEntity
import com.smakolyky.data.mapper.toDomain
import com.smakolyky.data.remote.MealDbApi
import com.smakolyky.domain.model.Area
import com.smakolyky.domain.model.Category
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val api: MealDbApi,
    private val recipeDao: RecipeDao,
) : RecipeRepository {

    override fun searchRecipes(query: String): Flow<List<Recipe>> = flow {
        try {
            val response = api.searchMeals(query)
            val recipes = response.meals.orEmpty().map { it.toDomain() }
            recipeDao.upsertAll(recipes.map { it.toEntity() })
            emit(recipes)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val cached = recipeDao.searchByTitle(query).first().map { it.toDomain() }
            if (cached.isEmpty()) throw e
            emit(cached)
        }
    }

    override fun getRecipe(id: String): Flow<Recipe?> = flow {
        val cached = recipeDao.getById(id)?.toDomain()
        if (cached != null && cached.isDetailLoaded) {
            emit(cached)
            return@flow
        }
        try {
            val response = api.lookupMeal(id)
            val recipe = response.meals.orEmpty().firstOrNull()?.toDomain()
            if (recipe != null) {
                recipeDao.upsert(recipe.toEntity())
                emit(recipe)
            } else {
                emit(cached)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (cached != null) emit(cached) else throw e
        }
    }

    override fun getCategories(): Flow<List<Category>> = flow {
        emit(api.getCategories().categories.orEmpty().map { it.toDomain() })
    }

    override fun getAreas(): Flow<List<Area>> = flow {
        emit(api.getAreas().meals.orEmpty().map { Area(it.strArea) })
    }

    override fun getRandomRecipe(): Flow<Recipe> = flow {
        val recipe = api.getRandomMeal().meals.orEmpty().first().toDomain()
        recipeDao.upsert(recipe.toEntity())
        emit(recipe)
    }

    // filter.php повертає лише id/назву/фото — решта довантажиться через getRecipe(id)
    override fun getByCategory(name: String): Flow<List<Recipe>> = flow {
        val summaries = api.filterByCategory(name).meals.orEmpty().map { it.toDomain() }
        emit(summaries)
    }

    override fun getByArea(name: String): Flow<List<Recipe>> = flow {
        val summaries = api.filterByArea(name).meals.orEmpty().map { it.toDomain() }
        emit(summaries)
    }
}
