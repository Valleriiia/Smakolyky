package com.smakolyky.domain.model

data class RecipeFilter(
    val category: String? = null,
    val area: String? = null,
) {
    val isEmpty: Boolean get() = category == null && area == null
}
