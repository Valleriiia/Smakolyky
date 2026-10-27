package com.smakolyky.data.local

import androidx.room.TypeConverter
import com.smakolyky.domain.model.RecipeIngredient
import com.smakolyky.domain.model.RecipeStep
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromIngredients(value: List<RecipeIngredient>): String = json.encodeToString(value)

    @TypeConverter
    fun toIngredients(value: String): List<RecipeIngredient> = json.decodeFromString(value)

    @TypeConverter
    fun fromSteps(value: List<RecipeStep>): String = json.encodeToString(value)

    @TypeConverter
    fun toSteps(value: String): List<RecipeStep> = json.decodeFromString(value)

    @TypeConverter
    fun fromTags(value: List<String>): String = json.encodeToString(value)

    @TypeConverter
    fun toTags(value: String): List<String> = json.decodeFromString(value)
}
