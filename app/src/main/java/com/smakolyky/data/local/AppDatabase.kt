package com.smakolyky.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smakolyky.data.local.dao.FavoriteDao
import com.smakolyky.data.local.dao.RecipeDao
import com.smakolyky.data.local.dao.ShoppingDao
import com.smakolyky.data.local.entity.FavoriteEntity
import com.smakolyky.data.local.entity.RecipeEntity
import com.smakolyky.data.local.entity.ShoppingItemEntity

@Database(
    entities = [RecipeEntity::class, FavoriteEntity::class, ShoppingItemEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun shoppingDao(): ShoppingDao
}
