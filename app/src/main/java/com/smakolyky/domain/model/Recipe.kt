package com.smakolyky.domain.model

// cookTimeMin/servings nullable TheMealDB не надає, заповнюються лише для власних рецептів
data class Recipe(
    val id: String,
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
    val cookTimeMin: Int? = null,
    val servings: Int? = null,
    val isCustom: Boolean = false,
) {
    // За цією ознакою RecipeRepository вирішує, чи довантажувати lookup.php
    // (filter.php повертає рецепти без інструкцій/інгредієнтів)
    val isDetailLoaded: Boolean
        get() = instructions.isNotBlank() && ingredients.isNotEmpty()
}
