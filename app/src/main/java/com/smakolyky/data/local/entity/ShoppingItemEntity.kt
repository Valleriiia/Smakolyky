package com.smakolyky.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String = "local",
    val ingredientName: String,
    val measure: String,
    val recipeId: String?,
    val recipeTitle: String?,
    val isChecked: Boolean = false,
    val addedAt: Long,
)
