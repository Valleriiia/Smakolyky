package com.smakolyky.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
    val categories: List<CategoryDto>? = null,
)
