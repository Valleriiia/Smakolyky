package com.smakolyky.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.smakolyky.data.local.Converters
import com.smakolyky.domain.model.Recipe
import com.smakolyky.domain.model.RecipeIngredient
import com.smakolyky.domain.model.RecipeStep

@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class RecipeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String?,
    val area: String?,
    val imageUrl: String?,
    val videoUrl: String?,
    val sourceUrl: String?,
    val tags: List<String>,
    val instructions: String,
    val ingredients: List<RecipeIngredient>,
    val steps: List<RecipeStep>,
    val cookTimeMin: Int?,
    val servings: Int?,
    val isCustom: Boolean,
    val cachedAt: Long, // для майбутньої інвалідації застарілого кешу; поки не використовується
)

fun RecipeEntity.toDomain(): Recipe = Recipe(
    id = id,
    title = title,
    category = category,
    area = area,
    imageUrl = imageUrl,
    videoUrl = videoUrl,
    sourceUrl = sourceUrl,
    tags = tags,
    instructions = instructions,
    ingredients = ingredients,
    steps = steps,
    cookTimeMin = cookTimeMin,
    servings = servings,
    isCustom = isCustom,
)

fun Recipe.toEntity(cachedAt: Long = System.currentTimeMillis()): RecipeEntity = RecipeEntity(
    id = id,
    title = title,
    category = category,
    area = area,
    imageUrl = imageUrl,
    videoUrl = videoUrl,
    sourceUrl = sourceUrl,
    tags = tags,
    instructions = instructions,
    ingredients = ingredients,
    steps = steps,
    cookTimeMin = cookTimeMin,
    servings = servings,
    isCustom = isCustom,
    cachedAt = cachedAt,
)
