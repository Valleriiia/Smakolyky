package com.smakolyky.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeStep(
    val stepNumber: Int,
    val description: String,
)
