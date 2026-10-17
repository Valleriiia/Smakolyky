package com.smakolyky.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String? = null,
    val strCategoryDescription: String? = null,
)
