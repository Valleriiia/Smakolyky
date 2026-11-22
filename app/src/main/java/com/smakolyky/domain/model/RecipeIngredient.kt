package com.smakolyky.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeIngredient(
    val name: String,
    val measure: String,
)
