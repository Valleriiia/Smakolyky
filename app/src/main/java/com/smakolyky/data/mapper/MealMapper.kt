package com.smakolyky.data.mapper

import com.smakolyky.data.remote.dto.CategoryDto
import com.smakolyky.data.remote.dto.MealDto
import com.smakolyky.data.remote.dto.MealSummaryDto
import com.smakolyky.domain.model.Category
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.model.RecipeIngredient
import com.smakolyky.domain.model.RecipeStep

fun MealDto.toDomain(): Recipe {
    val ingredients =
        ingredientPairs()
            .mapNotNull { (name, measure) ->
                val cleanName = name?.trim().orEmpty()
                if (cleanName.isEmpty()) return@mapNotNull null
                RecipeIngredient(name = cleanName, measure = measure?.trim().orEmpty())
            }

    return Recipe(
        id = idMeal,
        title = strMeal,
        category = strCategory,
        area = strArea,
        imageUrl = strMealThumb,
        videoUrl = strYoutube?.takeIf { it.isNotBlank() },
        sourceUrl = strSource?.takeIf { it.isNotBlank() },
        tags = strTags?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList(),
        instructions = strInstructions.orEmpty(),
        ingredients = ingredients,
        steps = strInstructions.toSteps(),
    )
}

// Якщо API повертає суцільний текст без абзаців, алгоритм використовує регулярний вираз
// для безпечного розбиття тексту на кроки по розділових знаках кінця речення
private fun String?.toSteps(): List<RecipeStep> {
    val text = this?.trim().orEmpty()
    if (text.isEmpty()) return emptyList()

    val byNewline =
        text
            .split(Regex("\r\n|\n|\r"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }

    val raw =
        if (byNewline.size > 1) {
            byNewline
        } else {
            text
                .split(Regex("(?<=[.!?])\\s+"))
                .map { it.trim() }
                .filter { it.isNotEmpty() }
        }

    return raw.mapIndexed { index, step -> RecipeStep(stepNumber = index + 1, description = step) }
}

fun MealSummaryDto.toDomain(): Recipe =
    Recipe(
        id = idMeal,
        title = strMeal,
        category = null,
        area = null,
        imageUrl = strMealThumb,
        videoUrl = null,
        sourceUrl = null,
        tags = emptyList(),
        instructions = "",
        ingredients = emptyList(),
        steps = emptyList(),
    )

fun CategoryDto.toDomain(): Category =
    Category(
        id = idCategory,
        name = strCategory,
        thumbnailUrl = strCategoryThumb,
        description = strCategoryDescription,
    )
