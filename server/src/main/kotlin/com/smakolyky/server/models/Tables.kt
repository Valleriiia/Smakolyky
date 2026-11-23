package com.smakolyky.server.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object Users : Table("users") {
    val id = long("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val displayName = varchar("display_name", 100).nullable()
    val avatarUrl = varchar("avatar_url", 500).nullable()
    val createdAt = timestamp("created_at")
    override val primaryKey = PrimaryKey(id)
}

object Recipes : Table("recipes") {
    val id = long("id").autoIncrement()
    val externalId = varchar("external_id", 50).nullable()
    val title = varchar("title", 255)
    val imageUrl = varchar("image_url", 500).nullable()
    val videoUrl = varchar("video_url", 500).nullable()
    val sourceUrl = varchar("source_url", 500).nullable()
    val categoryId = long("category_id").nullable()
    val areaId = long("area_id").nullable()
    val authorId = (long("author_id") references Users.id).nullable()
    val cookTimeMin = integer("cook_time_min").nullable()
    val servings = integer("servings").nullable()
    val isCustom = bool("is_custom").default(false)
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
    override val primaryKey = PrimaryKey(id)
}

object Favorites : Table("favorites") {
    val userId = long("user_id") references Users.id
    val recipeId = long("recipe_id") references Recipes.id
    val addedAt = timestamp("added_at")
    override val primaryKey = PrimaryKey(userId, recipeId)
}

object Ratings : Table("ratings") {
    val id = long("id").autoIncrement()
    val userId = long("user_id") references Users.id
    val recipeId = long("recipe_id") references Recipes.id
    val score = integer("score")
    val comment = text("comment").nullable()
    val createdAt = timestamp("created_at")
    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(userId, recipeId)
    }
}

object ShoppingItems : Table("shopping_items") {
    val id = long("id").autoIncrement()
    val userId = long("user_id") references Users.id
    val ingredientName = varchar("ingredient_name", 255)
    val measure = varchar("measure", 100).nullable()
    val recipeId = (long("recipe_id") references Recipes.id).nullable()
    val isChecked = bool("is_checked").default(false)
    val addedAt = timestamp("added_at")
    override val primaryKey = PrimaryKey(id)
}
