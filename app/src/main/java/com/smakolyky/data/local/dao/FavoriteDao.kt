package com.smakolyky.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.smakolyky.data.local.entity.FavoriteEntity
import com.smakolyky.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE userId = :userId AND recipeId = :recipeId")
    suspend fun delete(
        recipeId: String,
        userId: String = "local",
    )

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND recipeId = :recipeId)")
    fun isFavorite(
        recipeId: String,
        userId: String = "local",
    ): Flow<Boolean>

    @Transaction
    @Query(
        """
        SELECT recipes.* FROM recipes
        INNER JOIN favorites ON favorites.recipeId = recipes.id
        WHERE favorites.userId = :userId
        ORDER BY favorites.addedAt DESC
        """,
    )
    fun observeFavoriteRecipes(userId: String = "local"): Flow<List<RecipeEntity>>
}
