package com.smakolyky.data.local.entity

import androidx.room.Entity

@Entity(tableName = "favorites", primaryKeys = ["userId", "recipeId"])
data class FavoriteEntity(
    val userId: String = "local",
    val recipeId: String,
    val addedAt: Long,
)
