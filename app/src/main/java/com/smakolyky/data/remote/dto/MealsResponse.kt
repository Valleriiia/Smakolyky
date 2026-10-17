package com.smakolyky.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse(
    val meals: List<MealDto>? = null,
)

@Serializable
data class MealSummaryDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String? = null,
)

@Serializable
data class MealSummariesResponse(
    val meals: List<MealSummaryDto>? = null,
)
